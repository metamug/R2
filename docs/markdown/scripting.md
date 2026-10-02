[//]: # (Scripting)
[//]: # (https://metamug.com/img/docs/scripting/Groovy.png)
[//]: # (2020-02-27T14:00:00+00:00)
[//]: # (Add logic to your API with server-side Kotlin scripting)


Scripts are written in [Kotlin](https://kotlinlang.org/) (`.kts` files) and referenced from [resource files](/docs/resource-file) with the `<Script>` tag. A script can combine the request, earlier SQL results and external API responses, and its output becomes part of the response. Groovy scripts written for earlier versions keep working (see [Groovy scripts](#groovy-scripts-legacy)).

### Referencing a script

Save a script as `hello.kts` and reference it without the extension:

**hello.kts**
```kotlin
val name = params["name"] ?: "World"
response["message"] = "Hello $name"
```

**hello.xml**
```xml
<Request method="GET">
    <Script file="hello" id="greeting" output="true"/>
</Request>
```

`GET /v1.0/hello?name=John` returns

```js
{ "greeting": { "message": "Hello John" } }
```

### Variables available to a script

| name | type | what it is |
|---|---|---|
| `params` | `Map<String, String>` | request parameters (query string or form body, also for PUT): `params["qty"]?.toIntOrNull()` |
| `request` | Mason request | `request.id` (item id, `null` for a collection request), `request.body`, `request.method` |
| `response` | `MutableMap<String, Any?>` | the output of the step: whatever you put here is returned under the step's `id` |
| `steps` | `Map<String, Any?>` | results of the elements declared **before** the script, by `id` (see below) |
| `ds` | `javax.sql.DataSource` | the app's data source, for your own JDBC |

Put `import` lines at the top of the file. `print` output is not part of the response: assign to `response`.

### Combining SQL, scripts and external APIs in one request

The request below looks a product up through an external call, lets a script validate and price the order and sign it, writes
everything in one transaction when the script accepted the order, and returns the stored order:

```xml
<Request method="POST" status="201">
    <XRequest id="prod" method="GET" url="https://api.example.com/products/$product_id" output="false"/>
    <Script id="calc" file="ordercalc" output="true"/>
    <Transaction when="$[calc].ok eq true">
        <Sql id="o" type="update">INSERT INTO orders (customer_id, total, token) VALUES ($customer_id, $[calc].total, $[calc].token)</Sql>
        <Sql id="s" type="update">UPDATE product SET stock = stock - $qty WHERE id = $product_id</Sql>
    </Transaction>
    <Sql id="placed" when="$[calc].ok eq true" output="true">SELECT * FROM orders WHERE token = $[calc].token</Sql>
</Request>
```

**ordercalc.kts**
```kotlin
import java.math.BigDecimal
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Suppress("UNCHECKED_CAST")
val product = ((steps["prod"] as? Map<String, Any?>)?.get("one") as? List<Map<String, Any?>>)?.firstOrNull()
val qty = params["qty"]?.toIntOrNull() ?: 0

if (product == null || qty <= 0) {
    response["ok"] = false
    response["reason"] = "unknown product or bad quantity"
} else {
    val total = BigDecimal(product["price"].toString()).multiply(BigDecimal(qty))
    val mac = Mac.getInstance("HmacSHA256").apply { init(SecretKeySpec("secret".toByteArray(), "HmacSHA256")) }
    response["ok"] = true
    response["total"] = total
    response["token"] = mac.doFinal("${params["customer_id"]}:$total".toByteArray()).joinToString("") { "%02x".format(it) }.take(40)
}
```

- A later element reads a script result with [MPath](/docs/mpath): `$[calc].total`. Use it in `when="..."` to branch.
- In a script, `steps["id"]` holds an earlier element's result: SQL as a `List<Map<String, Any?>>` (column names keep the database's case: HSQLDB upper-case, PostgreSQL lower-case), XRequest as its parsed JSON body, a script or `Execute` as its response map.
- The declared `status` of the `<Request>` is fixed. Use `when` on later elements to skip work when a script rejects the request.
- `<Transaction>` commits or rolls back its SQL elements together.

### One script for item and collection requests

`request.id` is `null` for `/resource` and the id for `/resource/{id}`:

```kotlin
import java.security.MessageDigest
import java.util.UUID

val id = request.id
if (id != null) {
    response["sha256"] = MessageDigest.getInstance("SHA-256").digest(id.toByteArray()).joinToString("") { "%02x".format(it) }
} else {
    response["items"] = (1..(params["count"]?.toIntOrNull() ?: 3)).map { UUID.randomUUID().toString() }
}
```

Reference it from two requests of the same resource; element `id`s must be unique across **all** requests of a resource:

```xml
<Request method="GET"><Script id="many" file="tokens"/></Request>
<Request method="GET" item="true"><Script id="one" file="tokens"/></Request>
```

### Editing, errors and performance

- Scripts are compiled when first used and cached until the file changes. Saving a new version is picked up by the next request without a restart. The first call of a version takes about 0.5-4 s (compile), later calls add a few milliseconds (the script body is a function that is invoked per request).
- Because the body runs as the body of a function, declare helper functions and classes inside it (local ones are fine) and do not use a top-level `return`.
- A compile or runtime error returns HTTP 512 with an `errorId`. The cause, with the script line, is recorded in the app's error log and shown on the Console error screen, e.g. `hash.kts: Unresolved reference: nosuchmethod (line 2:20)`. Through the MCP server use the `get_errors` tool.
- The Kotlin script engine needs the `kotlin-scripting-jsr223` jars on the server classpath. `dev build` fetches them into `server/lib`.

### Groovy scripts (legacy)

Existing `.groovy` scripts keep working: reference them with the extension (`<Script file="greet.groovy" id="g"/>`), read request parameters as `_$name` and write to `response['key']`. Groovy scripts do not receive `steps`/`ds`. New scripts should use Kotlin.

```groovy
response['message'] = 'Hello ' + _$name
```

**Migrating** a Groovy script: rename to `.kts`, replace `_$name` with `params["name"]`, `response['k'] = v` with `response["k"] = v`, and drop the extension (or use `.kts`) in the `<Script file>` attribute. A script name can exist in one language only.
