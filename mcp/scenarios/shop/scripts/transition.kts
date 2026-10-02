// order status state machine: NEW -> PAID -> SHIPPED, NEW -> CANCELLED. The current row comes from the "cur" SQL step.
val allowed = mapOf(
    "NEW" to setOf("PAID", "CANCELLED"),
    "PAID" to setOf("SHIPPED"),
)
val current = (steps["cur"] as? List<*>)?.firstOrNull() as? Map<*, *>
val from = current?.entries?.firstOrNull { it.key.toString().equals("status", ignoreCase = true) }?.value?.toString()
val to = params["status"]?.uppercase()

response["from"] = from
response["to"] = to
response["ok"] = from != null && to != null && to in (allowed[from] ?: emptySet())
if (from == null) response["reason"] = "unknown order"
else if (response["ok"] == false) response["reason"] = "cannot go from $from to $to"
