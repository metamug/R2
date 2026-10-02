# Agent development benchmark: R2 (via MCP) vs Spring Boot

The same "shop" API built twice, then checked for the same behaviours:

- **R2**: `mcp/scenarios/shop/` (schema, 4 Kotlin scripts, 6 resource XML files), deployed through the MCP server.
- **Spring Boot**: `benchmarks/spring-shop/` (Spring Boot 2.7, `JdbcTemplate`, one controller), PostgreSQL.

Behaviours (30 on the Spring side, 37 on the R2 side with the 7 MCP/deploy checks): customer registration with a salted
SHA-256 password, unique email, collection / `?q=recent` / item requests; order placement that looks the product up through an
HTTP call, validates stock in a script, prices the order, signs an HMAC token, writes order + line + stock decrement in one
transaction, notifies an external API, and answers with a join; order filters, an order state machine; hashes/UUIDs from one
script for item and collection requests; transaction rollback and an external 500 that must not break the request.

## Run it

```
dev build && dev start                       # R2 on :7000
cd benchmarks/spring-shop && ../../server/maven/bin/mvn package -DskipTests && java -jar target/spring-shop-1.0.jar   # :8081, db springshop
node mcp/scenarios/shop/run.mjs benchp                         # R2 behaviours (set R2_DB=postgresql ... for PostgreSQL, BUNDLE=1 for apply_project)
node benchmarks/spring-checks.mjs                              # Spring behaviours
node benchmarks/measure.mjs benchp                             # sizes, latency, iteration time, memory
```

`results.json` holds the raw numbers of the run reported in `results.md`.
