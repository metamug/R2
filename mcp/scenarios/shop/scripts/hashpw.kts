import java.security.MessageDigest
import java.util.UUID

// salted SHA-256: stored as salt$hex
val password = params["password"] ?: ""
val salt = UUID.randomUUID().toString().substring(0, 8)
val digest = MessageDigest.getInstance("SHA-256").digest((salt + password).toByteArray())
response["salt"] = salt
response["hash"] = salt + "\$" + digest.joinToString("") { "%02x".format(it) }
