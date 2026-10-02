import java.math.BigDecimal
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

// Combines the product fetched by the "prod" XRequest with the request: validates stock, prices the order
// and signs it with an HMAC-SHA256 token. Column names are matched case-insensitively (HSQLDB upper-cases them).
fun Map<String, Any?>.ci(key: String): Any? = entries.firstOrNull { it.key.equals(key, ignoreCase = true) }?.value

@Suppress("UNCHECKED_CAST")
val product = ((steps["prod"] as? Map<String, Any?>)?.get("one") as? List<Map<String, Any?>>)?.firstOrNull()
val qty = params["qty"]?.toIntOrNull() ?: 0

if (product == null) {
    response["ok"] = false
    response["reason"] = "unknown product"
} else if (qty <= 0) {
    response["ok"] = false
    response["reason"] = "qty must be positive"
} else if ((product.ci("stock") as Number).toInt() < qty) {
    response["ok"] = false
    response["reason"] = "insufficient stock"
} else {
    val price = BigDecimal(product.ci("price").toString())
    val total = price.multiply(BigDecimal(qty))
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(SecretKeySpec("shop-demo-secret".toByteArray(), "HmacSHA256"))
    val payload = "${params["customer_id"]}:${params["product_id"]}:$qty:$total:${System.nanoTime()}"
    response["ok"] = true
    response["price"] = price
    response["total"] = total
    response["token"] = mac.doFinal(payload.toByteArray()).joinToString("") { "%02x".format(it) }.take(40)
}
