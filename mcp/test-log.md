# Dev server validation: run log

Environment: Tomcat 7000 on JDK 17; PostgreSQL 16.4 portable binaries in mcp/pgsql (port 5432, db r2test, trust auth) (+ jakarta jws/ws/soap/annotation API jars in `server/lib`), HSQLDB then PostgreSQL, driven only through `mcp/server.mjs`.

## Setup frictions (evidence for the plan)
- Shipped server needs Java 8 APIs (`javax.jws`, `javax.xml.ws`); fails to start the console/api webapps on JDK 17 until those jars are added. `bin/setenv.bat` also forces `JAVA_HOME=%CATALINA_BASE%\jdk`.
- The shipped console DB contains an app `api` whose `backend/api/config.json` is missing, so `GET /app` returned `{"message":"Query Error","status":409}` for every call until that app was deleted.
- `POST /app` returns 201 about 13 s before the app's own webapp is deployed; calls in that window 404 (no readiness signal).
- Console SQL endpoint: invalid `type` (e.g. `update`) silently returns `[{"status":204}]` and executes nothing. DDL must use `type=query`; `plsql` rejects non-function DDL.
- Saving a resource validates its SQL against the live DB, so tables must exist first (clear 422 message, good).

## Case 1: basic CRUD on one table (HSQLDB), 1 iteration after setup
- define_resource (POST + deploy) -> 201 / deployed:true; hot-deploy visible immediately, no restart.
- POST/GET/GET item/PUT/DELETE all work.
- Gaps seen:
  1. Declared `status="201|202|410"` on Request is not returned; every call is HTTP 200.
  2. PUT with `rating=3.0` stored NULL (POST with `4.5` and `2` stored fine). Typed-value handling suspect; not yet diagnosed.
  3. Column keys come back upper-case (`ID`, `NAME`) from HSQLDB.
  4. Empty `{}` body on writes (no id / affected-row info).

## Case 1 on PostgreSQL (create_app with dbtype=postgresql), 4 iterations
- `create_app` returned 201 but created none of the app's internal tables (`request_log`, `mtg_query_*`, ...). `createDefaultTablesForExternal` swallows all exceptions (log line commented out). Running `postgres_defaultTables.sql` by hand shows a real script bug: `usr_role_role_id_fkey` FK between varchar and bigint, so `usr_role` is never created.
- Without those tables, `define_resource` fails with HTTP 422 "Server error. Could not save query references!" (no root cause surfaced), yet leaves a half-written `movie.jsp` (`Unterminated <m:request`); later calls then 500 with `ClassNotFoundException` for the JSP class. The JSP/Servlet layer is directly visible to the agent here.
- After tables were created by hand: collection GET works, but every request with a path/query parameter returns HTTP 512 `{"errorId":...}` with no cause. Only `catalina.*.log` shows it: `operator does not exist: integer = character varying`. Parameters are bound as strings; HSQLDB tolerates this, PostgreSQL does not. Typed parameter handling (plan item 3) is not implemented for JDBC binding.
- Workaround found: `CAST($id AS INTEGER)` / `CAST($rating AS NUMERIC)` in the SQL. With it CRUD works end to end on PostgreSQL.
- Same gaps as HSQLDB reproduce: status 200 instead of declared 201/202/410, and `PUT rating=3.0` stores NULL (so this is R2 parameter handling, not the DB).
- MCP gap: no tool exposes server error logs by `errorId`; the agent cannot diagnose a 512 without file access.

## Not yet run
Cases 2-10, and the Oracle / SQL Server repeats.

---

# Update: Kotlin scripting, composed APIs and MCP coverage (metamug/R2#92)

Scenario suites (all driven only through the MCP tools): `mcp/scenarios/shop/run.mjs` 48/48 on HSQLDB and PostgreSQL
(37/37 with `apply_project`), `mcp/scenarios/tools.mjs` 25/25 (all 19 tools). Spring Boot comparison in `benchmarks/`.

Fixed (Mason PRs #175-#180 and R2 `main`): Kotlin runner (hot reload, typed variables, `steps`), invalid JSP from
`<Transaction>`, every internal error recorded in `error_log` with its real message, Groovy scripts getting no parameters,
pooled tag handlers leaking XRequest headers/body, MOXy not working on JDK 17, per-response JAXB context, form bodies on PUT,
declared status codes, PostgreSQL string-bound parameters, re-uploading a script silently doing nothing.

Open (issues): script eval ~28 ms (#93), invisible XRequest failures (#94), scripts cannot set HTTP status (#95),
app naming/delete-recreate/duplicate-id messages (#96), docs on EL null semantics and Oracle/SQL Server not tested (#97).
