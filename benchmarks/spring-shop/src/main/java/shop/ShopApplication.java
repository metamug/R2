package shop;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}

@RestController
@RequestMapping("/v1.0")
class ShopController {

    private final JdbcTemplate jdbc;
    private final RestTemplate rest = new RestTemplate();

    ShopController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ---------- customer ----------

    @PostMapping("/customer")
    ResponseEntity<Map<String, Object>> register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String salt = UUID.randomUUID().toString().substring(0, 8);
        String hash = salt + "$" + hex(sha256((salt + password).getBytes(StandardCharsets.UTF_8)));
        try {
            jdbc.update("INSERT INTO customer (name, email, pw_hash) VALUES (?, ?, ?)", name, email, hash);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "email already registered"));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("created", jdbc.queryForList("SELECT id, name, email FROM customer WHERE email = ?", email)));
    }

    @GetMapping("/customer")
    Object customers(@RequestParam(required = false) String q) {
        if ("recent".equals(q)) {
            return jdbc.queryForList("SELECT id, name, email FROM customer ORDER BY id DESC LIMIT 2");
        }
        return jdbc.queryForList("SELECT id, name, email FROM customer ORDER BY id");
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<Object> customer(@PathVariable int id) {
        List<Map<String, Object>> rows = jdbc.queryForList("SELECT id, name, email FROM customer WHERE id = ?", id);
        return rows.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(rows.get(0));
    }

    // ---------- product ----------

    @GetMapping("/product/{id}")
    ResponseEntity<Object> product(@PathVariable int id) {
        List<Map<String, Object>> rows = jdbc.queryForList("SELECT id, sku, name, price, stock FROM product WHERE id = ?", id);
        return rows.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(rows.get(0));
    }

    // ---------- order ----------

    @PostMapping("/order")
    @Transactional
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestParam("customer_id") int customerId, @RequestParam("product_id") int productId, @RequestParam int qty) {
        // product lookup through the HTTP API of the same server, as the R2 resource does with an XRequest
        Map<String, Object> product;
        try {
            product = rest.getForObject("http://localhost:8081/v1.0/product/" + productId, Map.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("ok", false, "reason", "unknown product"));
        }
        if (qty <= 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("ok", false, "reason", "qty must be positive"));
        }
        if (((Number) product.get("stock")).intValue() < qty) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("ok", false, "reason", "insufficient stock"));
        }
        BigDecimal price = new BigDecimal(product.get("price").toString());
        BigDecimal total = price.multiply(BigDecimal.valueOf(qty));
        String token = hmac(customerId + ":" + productId + ":" + qty + ":" + total + ":" + System.nanoTime()).substring(0, 40);

        Integer orderId = jdbc.queryForObject("INSERT INTO orders (customer_id, total, status, token) VALUES (?, ?, 'NEW', ?) RETURNING id",
                Integer.class, customerId, total, token);
        jdbc.update("INSERT INTO order_line (order_id, product_id, qty, price) VALUES (?, ?, ?, ?)", orderId, productId, qty, price);
        jdbc.update("UPDATE product SET stock = stock - ? WHERE id = ?", qty, productId);

        notifyExternal(token, total);

        Map<String, Object> body = new HashMap<>();
        body.put("ok", true);
        body.put("total", total);
        body.put("token", token);
        body.put("placed", jdbc.queryForList(
                "SELECT o.id, o.total, o.status, o.token, c.name AS customer FROM orders o JOIN customer c ON c.id = o.customer_id WHERE o.id = ?", orderId));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/order")
    List<Map<String, Object>> orders(@RequestParam(name = "customer_id", required = false) Integer customerId, @RequestParam(required = false) String status) {
        if (customerId != null) {
            return jdbc.queryForList("SELECT id, customer_id, total, status FROM orders WHERE customer_id = ? ORDER BY id", customerId);
        }
        if (status != null) {
            return jdbc.queryForList("SELECT id, customer_id, total, status FROM orders WHERE status = ? ORDER BY id", status);
        }
        return jdbc.queryForList("SELECT id, customer_id, total, status FROM orders ORDER BY id");
    }

    @GetMapping("/order/{id}")
    ResponseEntity<Map<String, Object>> order(@PathVariable int id) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT o.id, o.total, o.status, c.name AS customer, c.email FROM orders o JOIN customer c ON c.id = o.customer_id WHERE o.id = ?", id);
        if (rows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> body = new HashMap<>(rows.get(0));
        body.put("lines", jdbc.queryForList(
                "SELECT p.sku, p.name, l.qty, l.price FROM order_line l JOIN product p ON p.id = l.product_id WHERE l.order_id = ?", id));
        return ResponseEntity.ok(body);
    }

    private static final Map<String, Set<String>> TRANSITIONS = Map.of("NEW", Set.of("PAID", "CANCELLED"), "PAID", Set.of("SHIPPED"));

    @PutMapping("/order/{id}")
    ResponseEntity<Map<String, Object>> move(@PathVariable int id, @RequestParam String status) {
        List<String> current = jdbc.queryForList("SELECT status FROM orders WHERE id = ?", String.class, id);
        if (current.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "reason", "unknown order"));
        }
        String from = current.get(0);
        String to = status.toUpperCase();
        if (!TRANSITIONS.getOrDefault(from, Set.of()).contains(to)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("ok", false, "reason", "cannot go from " + from + " to " + to));
        }
        jdbc.update("UPDATE orders SET status = ? WHERE id = ?", to, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("ok", true, "from", from, "to", to));
    }

    // ---------- token ----------

    @GetMapping("/token/{id}")
    Map<String, Object> tokenItem(@PathVariable String id) throws Exception {
        byte[] bytes = id.getBytes(StandardCharsets.UTF_8);
        return Map.of("id", id, "sha256", hex(sha256(bytes)), "md5", hex(MessageDigest.getInstance("MD5").digest(bytes)),
                "base64", Base64.getEncoder().encodeToString(bytes), "uuid", UUID.nameUUIDFromBytes(bytes).toString());
    }

    @GetMapping("/token")
    Map<String, Object> tokens(@RequestParam(defaultValue = "3") int count) {
        int n = Math.max(1, Math.min(20, count));
        return Map.of("count", n, "items", IntStream.rangeClosed(1, n).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList()));
    }

    // ---------- failure semantics ----------

    @PostMapping("/txtest")
    @Transactional
    public void txtest(@RequestParam("customer_id") int customerId, @RequestParam(name = "order_ref", required = false) Integer orderRef) {
        jdbc.update("INSERT INTO orders (customer_id, total, status, token) VALUES (?, 1, 'TX', 'tx-rollback-test')", customerId);
        jdbc.update("INSERT INTO order_line (order_id, product_id, qty, price) VALUES (?, 1, 1, 1)", orderRef); // NOT NULL violation
    }

    @GetMapping("/xfail")
    Map<String, Object> xfail() {
        try {
            rest.getForObject("https://postman-echo.com/status/500", String.class);
        } catch (Exception ignored) {
            // an external failure must not fail the request
        }
        return Map.of("after", jdbc.queryForList("SELECT COUNT(*) AS n FROM product"));
    }

    // ---------- helpers ----------

    private void notifyExternal(String token, BigDecimal total) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            rest.postForEntity("https://postman-echo.com/post",
                    new HttpEntity<>(Map.of("event", "order.placed", "token", token, "total", total.toString()), headers), String.class);
        } catch (Exception ignored) {
            // notification is best effort
        }
    }

    private static byte[] sha256(byte[] in) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String hmac(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec("shop-demo-secret".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return hex(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
