# Results (2026-10-02, one machine, Windows, JDK 17, PostgreSQL 16.4)

Raw numbers: `results.json`. Both implementations pass their behaviour suites (R2 48/48 and 37/37 with `apply_project`;
Spring 30/30).

## What an agent has to write

| | R2 (XML + Kotlin) | Spring Boot |
|---|---|---|
| files | 11 | 4 |
| lines | 188 | 295 |
| characters | 10,942 (~2.7k tokens) | 13,584 (~3.4k tokens) |

About 20% fewer characters and 36% fewer lines for R2. XML is verbose, so the gap is smaller than the line count suggests;
the saving comes from not writing controllers, DTO plumbing, transaction wiring and HTTP client code.

## Tokens through the tool channel (proxy: characters / 4)

| R2 flow | tool calls | chars sent | chars received | ~tokens |
|---|---|---|---|---|
| per-item tools (`define_resource`, `upload_script`, `run_sql`...) | 53 | 15,374 | 5,713 | 5,272 |
| `apply_project` (folder deployed in one call; files are read by the MCP server) | 36 | 2,692 | 4,740 | 1,858 |

Most of the remaining 36 calls are verification calls the scenario makes (`call_endpoint`, `run_sql`), which a human or an
agent would also do. One-time onboarding cost: `tools/list` 6.8k chars (~1.7k tokens) and `r2://guide` 4.5k chars (~1.1k tokens).

Per-item tools were token-inefficient: resource XML is re-sent inside JSON arguments (+41% over the file size) and every
step costs a round trip. Reading files from disk (`apply_project`) fixed most of it. Spring Boot needs the 3.4k tokens of
source plus shell commands and their output (a few hundred tokens), with no onboarding because the model already knows it.

Net, for this API: R2 is roughly on par with Spring Boot per task once `apply_project` is used (about 2.7k authored + 1.9k
tool traffic + a one-time 2.8k onboarding, versus about 3.4k authored + a few hundred for commands). It is **not** clearly
cheaper in tokens; the advantage is the loop speed below.

## Iteration speed (edit to the change being served)

| change | R2 | Spring Boot |
|---|---|---|
| edit a script, call it | 0.45 - 0.6 s (first edit ~0.8 s) | n/a |
| edit a resource (save, validate SQL against the live DB, deploy, call) | 0.6 - 0.9 s | n/a |
| edit code | | 8.1 s (rebuild 4.8 s + restart 3.3 s) |
| bring up a brand new API | ~13 s until the new app is served (+ ~4 s first script compile) | first build 22 s (dependency download), 4.8 s warm, 3.4 s start |

Spring Boot DevTools or JRebel would shrink the Spring number; none was configured.

## Runtime cost

| | R2 | Spring Boot |
|---|---|---|
| GET item, 2 joins (median) | 4.9 ms | 1.6 ms |
| GET script-only (median) | 31.8 ms | 1.0 ms |
| POST order (median; includes an external call) | 425 ms | 251 ms |
| memory | 2.1 GB (Tomcat with the Console and ~20 test apps; not comparable) | 191 MB (one app) |

R2's script path costs ~28 ms per call inside the Kotlin JSR-223 `CompiledScript.eval` (measured). The Kotlin scripting host API
used directly should remove most of it (follow-up in metamug/R2#92).

## Defects met while building each

- **Spring Boot**: one, found by the rollback behaviour check on the first run: `@Transactional` on a package-private
  method is silently ignored in Spring 5, so nothing was rolled back. Fixed by making the methods public (8.1 s loop).
- **R2**: many platform defects, all reported and mostly fixed in this effort (see `mcp/test-log.md`): invalid JSP for
  `<Transaction>`, Groovy scripts receiving no parameters, pooled tag handlers leaking an XRequest's headers/body into the next
  one, string-bound parameters on PostgreSQL, PUT bodies not parsed, ignored status codes, errors that never reached the
  error log, a per-response JAXB context.

## Caveats

- One author (an AI agent) built both; the tokens are character-based estimates, not metered usage.
- R2's numbers include a development effort dominated by fixing the platform, which is not a steady-state cost.
- Small, single-machine, single-run measurements; latency numbers are medians of 100-200 requests.
- Oracle and SQL Server were not run (no instances available).
