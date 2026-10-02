# Assessment of R2 and Mason (2026-10-02)

Written by the agent that ran the Dev server validation (metamug/R2#83-#97) and built the Kotlin/MCP/benchmark work (#92).
Everything marked **verified** was run on this machine (Windows, JDK 17, PostgreSQL 16.4, HSQLDB, Tomcat 9.0.16). Everything
marked **unverified** is inference. Numbers come from `mcp/scenarios`, `benchmarks/results.md` and `mcp/test-log.md`.

## 1. Bottom line

- The core loop is real and good: an agent can create an app, tables, scripts and resources through MCP, hot-deploy in
  0.5-0.9 s, call the API and read the real cause of failures. A realistic shop API (customers with salted hashes, orders
  with an HTTP product lookup, a Kotlin validation/pricing/HMAC script, a SQL transaction, an external notification, joins, a
  state machine) runs 48/48 checks on HSQLDB and on PostgreSQL.
- The platform was **not** ready for that when this started: it did not boot on JDK 17, `<Transaction>` generated invalid JSP,
  Groovy scripts received no parameters, PUT bodies were dropped, status codes were ignored, errors were unobservable, and
  PostgreSQL could not take any parameterised request. I fixed or worked around all of these; most fixes are in Mason pull
  requests that are **not merged yet**. The R2 app template vendors a Mason jar that contains all of them.
- Verdict on the original go/no-go (#83 plan): "works, but specific gaps need fixing first" has become "works, with the fixes
  merged and the remaining gaps in section 6". It is **not** yet evidence that R2 is cheaper for an agent than a conventional
  stack (section 5). Its measurable advantages are loop speed (0.5-0.9 s vs 8.1 s) and about 20% less code to author.

## 2. How the pieces fit

- **Mason** (github.com/metamug/mason, version 4.8, `develop`): the runtime. A taglib (`m:resource`, `m:request`, `m:execute`,
  `m:param` ...) plus a `Router` filter, request/response model, JSON/XML output, `XRequest` (external calls),
  `RequestProcessable` plugins (Groovy runner, now a Kotlin runner), `ExceptionTagHandler` (error responses). `sdk` has the
  `Request`/`Response`/`RequestProcessable` types. Each R2 app is a webapp whose `WEB-INF/lib` contains the Mason jar.
- **R2** (github.com/metamug/R2, default branch now `main`): `parser/` turns resource XML into a JSP (JSTL `sql:*`, `c:if` for
  `when`, `m:*` tags); `console/` is the management API (apps, resources, scripts, queries, deployment, error log, stats);
  `server/` is a Tomcat distribution; `cli/` is an old Groovy CLI; `mcp/` is new (Node MCP server, 19 tools, 20 resources,
  scenarios); `benchmarks/` is new (Spring Boot comparison). Apps are created from `console/.../app-template-v0.1.zip` (a binary in git
  that embeds the Mason jar) and each gets its own webapp and database.
- Execution path of a request: Tomcat -> app webapp -> Mason `Router` -> generated JSP (compiled by Jasper) -> tag handlers run
  the steps in order -> `JSONOutput` serialises the step results.
- `dev build|start|stop|status|mason` (`dev.cmd`, `dev.sh`) builds with the bundled Maven 3.6 (needs `--add-opens` on JDK 16+).
  `dev mason` builds Mason from `MASON_HOME` and refreshes the template.

## 3. What was fixed or added

R2 `main` (all pushed): JDK 17 boot (jakarta jws/ws/soap jars, `setenv.bat`), EclipseLink MOXy 2.6.4 -> 2.7.15 (2.6.4 cannot detect
Java 17, so every script response was empty), PostgreSQL datasource `stringtype=unspecified` (typed params), Tomcat
`parseBodyMethods` for PUT, `status` attribute emitted, `<Transaction>` JSP fix, PostgreSQL default-tables script (bad FK) and
non-silent failure, JSP written only on success, stale `api` app removed, invalid `/query` type -> 400, script create/update
semantics (409 instead of a silent no-op), Kotlin script support in parser/console (`.kts` default, `.groovy` still works), MCP server and
scenarios, benchmark, docs (`scripting.md` rewritten Kotlin-first, `mpath.md` corrected), `dev.*` scripts.

Mason PRs (open, each from `develop`):

| PR | Content | Notes |
|---|---|---|
| #175 `fix/request-status` | `status` attribute on `m:request` applied before output | needs the R2 parser change (already on `main`) |
| #176 `feature/kotlin-runner` | `KotlinRunner`: typed `params/request/response/steps/ds`, per-version compile cache, script evaluated once into a function and invoked per request (4.5 ms vs 31.8 ms), line-accurate errors | no compile-time Kotlin dependency; jars come from `server/kotlin/pom.xml` |
| #177 `feature/error-log` | every internal error written to `error_log` with message and trace; `ExecuteTagHandler` reports the real cause and passes earlier step results (`__steps`) to runners | `steps` in #176 needs this |
| #178 `fix/tag-handler-state` | `RequestTag` headers/params and `XRequest` body no longer leak between pooled tag-handler uses | bug: a POST XRequest corrupted the next GET XRequest |
| #179 `fix/param-map` | `RequestParamMap` enumerates request parameters (Groovy `_$name` variables were never bound) | |
| #180 `perf/response-marshalling` | no JAXBContext per response; Maps serialised directly | |

Mason issues #171-#174 mirror the R2 issues; #171/#172 were fixed in R2 config, #173 by PR #175, #174 by #177.
The Mason checkout at `D:\projects\mason` had someone else's uncommitted changes when I started; I never touched them. A scratch
build combines the branches (`build_mason.sh` overlay, not in the repo).

## 4. Verification status

| Area | Result |
|---|---|
| Shop scenario through MCP (`mcp/scenarios/shop/run.mjs`) | 48/48 HSQLDB, 48/48 PostgreSQL, 37/37 with `apply_project` |
| MCP tools (`mcp/scenarios/tools.mjs`) | 25/25, all 19 tools covered |
| Spring Boot reference app (`benchmarks/`) | 30/30 |
| Parser unit tests | 31 run, 2 failures + 6 errors, **identical with and without my change** (pre-existing) |
| Console unit tests | not run |
| **Not tested** | Oracle, SQL Server, auth/roles, pagination, file upload, OpenAPI/doc generation, multiple resource versions or `parent` resources, custom Java `Execute` classes, `Text`, XML/dataset output, CORS, concurrency/load (only sequential), security |

## 5. Agent development: R2 via MCP vs Spring Boot (`benchmarks/results.md`)

- Authored size: R2 188 lines / 10.9k chars / 11 files; Spring 295 lines / 13.6k chars / 4 files (about 20% fewer chars).
- Tokens (characters/4 proxy, not metered): per-item MCP tools were wasteful (53 calls, ~5.3k tokens, XML re-sent inside JSON +41%).
  `apply_project` (folder read by the MCP server) cut it to 36 calls / ~1.9k tokens. Onboarding adds ~2.8k tokens once (tool list + guide).
  Net: roughly on par with Spring (~3.4k authored + a few hundred for commands). Not clearly more token efficient.
- Loop: edit to served 0.45-0.9 s (script/resource) vs 8.1 s for Spring rebuild+restart (DevTools would shrink that).
  A brand new R2 app takes ~13 s before it is served; first Kotlin compile 0.5-4 s.
- Runtime: item+joins 5.0 ms vs 1.4; script-only 4.5 ms vs 0.8; order POST (with an external call) 368 ms vs 251.
- Memory: Tomcat hosting ~20 apps + Console + Kotlin compiler 1.0 GB vs 194 MB for one Spring app (not comparable, but each R2 app
  being a webapp is a cost).
- Defects met: Spring 1 (silent `@Transactional` on a package-private method, caught by a rollback check); R2 ~15 platform defects.
  The R2 figure reflects a young platform, not steady state.
- Caveat: one agent built both; single machine, single run.

## 6. Architectural assessment and risks

**The JSP translation layer** (the question #83 asked). Evidence from this run, all **verified**:
- Generated JSP was invalid for `<Transaction>` (JSTL forbids `dataSource` on nested sql tags) and the resource still saved and
  "deployed"; every call then failed. A failed save left a half-written JSP and later calls returned `ClassNotFoundException`.
- Parameter binding goes through JSTL `sql:param`, i.e. strings: PostgreSQL needed a driver option to work at all, other DBs rely on
  implicit conversion.
- Tag handlers are pooled by Jasper; state kept in handler fields leaked between uses (#178). Any new tag is exposed to this.
- Errors surface as JSP/Jasper exceptions; the client got an opaque 512 until #177.
- Each app is a webapp: ~13 s to come up, a classloader and its libs per app, ~1 GB for 20 apps.
- What JSP gave for free: hot recompile, EL (with sharp edges: a missing parameter is `null`, `$x ne ''` is true for it), JSTL SQL.
My read: nothing here blocks a Dev server, but the generator/JSP/tag-pool combination is where most defects came from. A direct
object-model router (parse XML -> execute steps in Java, one shared runtime, no per-app webapp) would remove the translation
bugs, the 13 s app start and the per-app memory. It is a larger project; it is justified by the defect and cost evidence, not required to ship.

**Mason as a dependency.** Version 4.8 on Java 8-era libraries (Tomcat 9.0.16, EclipseLink 2.6.4, Groovy 2.4.15, okhttp 3.10), sparse
maintenance (open issues from 2022), mixed JSON stacks (org.json, json-simple, MOXy/JAXB), no CI visible, small test surface. Apps copy the
Mason jar at creation, so existing apps do not get fixes; upgrades need recreation. The R2 template embeds a binary jar built from
unmerged branches: **the PRs must be merged and a Mason release consumed from Maven instead of a vendored jar.**

**Security (unverified unless stated).**
- **Verified:** `GET /console/app` returns external database passwords in plain text (`app_db_pw`); default login `admin/admin`; CORS `*` on the Console.
- Scripts run in-process with full JVM access and the app DataSource (no sandbox); this is by design for a dev server but unacceptable for multi-tenant use.
- `XRequest` bodies are templated with `$param`/`$[mpath]`; I did not test whether values are JSON-escaped (possible injection into the outgoing body).
- SQL values are bound as `?` parameters (not string-concatenated), which is the safe part.

**Product gaps found (issues).** XRequest failures invisible (#94); scripts cannot set the HTTP status, so a rejected order returns the
declared 201 (#95); app names are letters only, with misleading 401s for a non-existent app, delete-then-recreate leaves a half-extracted
webapp, duplicate-id errors do not name the id (#96); EL null semantics, save-time SQL execution and column-name case undocumented (#97);
`XRequest` mpath docs were wrong (`.body.`), Groovy `_$["q"]` step access documented but not implemented (docs fixed).

**Repo hygiene.** 133 MB of jars in `server/lib` (pack size ~99 MB), a binary template zip, `server/databases/mtg_console` is tracked
and rewritten by every run, parser tests partly failing, no CI, mixed LF/CRLF.

## 7. MCP assessment

19 tools (apps, SQL, scripts, resources, deploy, runtime calls, errors, stats, `apply_project`, `wait_for_app`) and 20 resources
(`r2://guide`, repo docs, tested example resources/scripts). Not covered: datasources, users/roles/auth, query catalog, import/export,
OpenAPI. Strengths: `get_errors` closes the opaque-512 gap; `call_endpoint` hints at it; `apply_project` is token-cheap. Weak points:
no prompts, no batching of verification calls, tool responses are text (HTTP-status convention) rather than structured, the test suite needs a
running server and is not in CI, login retries mask a transient 401 I could not reproduce.

## 8. Recommended next steps (priority order)

1. Merge Mason #175-#180, cut a Mason release, make the R2 template consume it (drop the vendored jar); add CI (build, parser tests, the two scenario suites).
2. Fix the structural JSP issues at the source: validate generated JSP at save time (compile it) and make a failed save atomic; decide on the direct router.
3. Close the product gaps #94, #95, #96; document #97.
4. Security pass: stop returning DB passwords, change default credentials flow, decide the sandbox story for scripts, test XRequest body escaping.
5. Extend verification: auth/roles, pagination, versions/`parent`, uploads, concurrency, Oracle/SQL Server (needs instances), Console unit tests.
6. MCP: datasources/users/import-export tools, structured results, CI-run suite.
7. Re-run the benchmark with Spring DevTools and a metered token count before drawing conclusions about cost.

## 9. For the next agent (practical)

- Run: `dev build && dev start`; Console `http://localhost:7000/console` (admin/admin); apps at `http://localhost:7000/{app}/v1.0/{resource}`.
- Test: `node mcp/scenarios/shop/run.mjs <newLettersOnlyAppName>` (add `BUNDLE=1`, `R2_DB=postgresql R2_DB_URL=host:5432/db R2_DB_USER=.. R2_DB_PASS=..`),
  `node mcp/scenarios/tools.mjs`, `node benchmarks/spring-checks.mjs` (Spring on :8081, db `springshop`).
- Local PostgreSQL: portable 16.4 in `mcp/pgsql` (git-ignored), `pg_ctl -D mcp/pgdata start`.
- Gotchas: app names letters only and use a **new** name per run (delete/recreate races); create_app returns ~13 s before serving (`wait_for_app`);
  the template jar and the console WAR must both be rebuilt for new apps to see a Mason change (existing apps need the jar copied);
  git needs a per-command identity and `-c safe.directory=D:/projects/mason` for the Mason checkout (not changed in config);
  files may be CRLF, so scripted edits must handle it; `server/databases/mtg_console` is rewritten by running the server (`git checkout` it before committing).
- Read first: `docs/markdown/scripting.md`, `mcp/guide.md`, `mcp/test-log.md`, `benchmarks/results.md`, issues #92-#97.
