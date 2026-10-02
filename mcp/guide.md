# R2 authoring guide (for agents)

A backend (app) has tables, resources (XML) and scripts (Kotlin). Workflow, all through MCP tools:

1. `create_app` (**letters only** in app and resource names; anything else is rejected and later calls answer a misleading "Invalid credentials") then **`wait_for_app`** (create returns ~10-40 s before the app is served).
2. `run_sql` (type `query`, also for DDL/DML) to create tables.
3. `upload_script` for `.kts` files, `define_resource` for XML (saves, validates SQL against the live DB, hot-deploys).
4. `call_endpoint` to test: resources are served at `/{app}/v{version}/{resource}` and `/{app}/v1.0/{resource}/{id}` for item requests.
5. On HTTP 512 take the `errorId` and call `get_errors`: it returns the real message (SQL error, script compile error with line).

## Resource XML

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
  <Request method="POST" status="201">          <!-- status = HTTP status of the response -->
    <Script id="h" file="hashpw" output="false"/> <!-- hashpw.kts; output=false hides the step from the response -->
    <Sql id="ins" type="update" output="false">INSERT INTO t (a) VALUES ($name, $[h].hash)</Sql>
    <Sql id="created" output="true">SELECT * FROM t WHERE a = $name</Sql>
  </Request>
  <Request method="GET">...</Request>              <!-- collection: /resource -->
  <Request method="GET" item="true">...</Request>  <!-- item: /resource/{id}, use $id -->
</Resource>
```

- `$name` = request parameter (query string or form body, also on PUT). `$id` = item id. Bound as text; PostgreSQL infers the column type.
- `$[step].path` = mpath: a field of an earlier step's result (script response, SQL row, XRequest body), e.g. `$[h].hash`, `$[prod].one[0].SKU` (an XRequest result is its parsed JSON body; `.body.` is not part of the path).
- `when="..."` on any step is an EL condition: `$q eq 'recent'`, `$[calc].ok eq true`, `not empty $x`. A **missing** parameter is null, so `$x ne ''` is true for it: use `empty $x` / `not empty $x`.
- Step `id`s must be unique across **all** requests of a resource (not just within one request).
- `<Sql type="update">` for INSERT/UPDATE/DELETE, default is a query. `<Transaction>` wraps several `<Sql type="update">` that commit or roll back together.
- `<XRequest id=".." method="GET|POST" url="..." output="false">` calls another API; `<Header name value/>`, `<Param name value/>`, `<Body>json with $params and $[mpath]</Body>`. A non-2xx answer does **not** fail the request.
- Save-time validation runs your SQL (prepared / rolled back): literal NULLs into NOT NULL columns fail the save, so use parameters for values that are tested at runtime.
- Escape `<`, `>` in SQL as `lt`, `gt` or use CDATA. Response keys: the step ids; SQL column names keep the database's case (HSQLDB upper-case, PostgreSQL lower-case).

## Kotlin scripts (`.kts`)

Reference with `<Script id="x" file="name"/>` (file `name.kts`). Declared variables, no imports needed for them:

| name | type | use |
|---|---|---|
| `params` | `Map<String, String>` | request parameters: `params["qty"]?.toIntOrNull()` |
| `request` | Mason request | `request.id` (item id or null), `request.body`, `request.method` |
| `response` | `MutableMap<String, Any?>` | the step output: `response["total"] = total` |
| `steps` | `Map<String, Any?>` | earlier step results by id: SQL as `List<Map>`, XRequest as its JSON map |
| `ds` | `javax.sql.DataSource` | run your own JDBC |

```kotlin
import java.security.MessageDigest
val pw = params["password"] ?: ""
response["hash"] = MessageDigest.getInstance("SHA-256").digest(pw.toByteArray()).joinToString("") { "%02x".format(it) }
```

- Put `import` lines at the top. Edits are hot (recompiled on the next call: first call after a change ~0.4-4 s, then ~50 ms).
- Compile errors come back through `get_errors` with the script line (`hash.kts: Unresolved reference: x (line 2:20)`).
- Branch on a script result with `when="$[calc].ok eq true"` on later steps. The declared `status` of the request is static.
- A script with an explicit `.groovy` name keeps working (`_$name` variables, `response['k'] = v`).

## Gotchas

- SQL column names in `steps` rows follow the database case: match case-insensitively if you target both HSQLDB and PostgreSQL.
- Re-creating an app with the same name right after deleting it can leave a half-extracted webapp: use a new name.
- Re-uploading an existing script or resource updates it (the tools do create-or-update).
