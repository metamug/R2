import java.security.MessageDigest
import java.util.Base64
import java.util.UUID

// One script serving an item request (/token/{id}: deterministic digests of the id)
// and a collection request (/token?count=N: N random UUIDs).
val id = request.id
if (id != null) {
    val bytes = id.toByteArray()
    response["id"] = id
    response["sha256"] = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
    response["md5"] = MessageDigest.getInstance("MD5").digest(bytes).joinToString("") { "%02x".format(it) }
    response["base64"] = Base64.getEncoder().encodeToString(bytes)
    response["uuid"] = UUID.nameUUIDFromBytes(bytes).toString()
} else {
    val n = (params["count"]?.toIntOrNull() ?: 3).coerceIn(1, 20)
    response["count"] = n
    response["items"] = (1..n).map { UUID.randomUUID().toString() }
}
