# r2-dev-mcp

Dependency-free MCP (stdio) server that wraps the R2 Console HTTP API, so an agent can define, hot-deploy and test REST resources on the Dev server. It is the MCP surface the "Dev Server Validation: Test Plan" assumes.

## Tools

| Tool | Console call |
|---|---|
| `list_apps` | `GET /app` |
| `list_resources` | `GET /app/{app}/rpx` |
| `get_resource` | `GET /app/{app}/rpx/{version}/{name}` |
| `define_resource` | `POST /app/{app}/rpx` (falls back to `PUT` on 409), then deploys |
| `delete_resource` | `DELETE /app/{app}/rpx/{version}/{name}` |
| `deploy_backend` / `deployment_status` | `POST` / `GET /app/{app}/deployment` |
| `upload_script` | `POST /app/{app}/script` (Kotlin etc.) |
| `create_app` | `POST /app` (internal HSQLDB or external DB) |
| `run_sql` | `POST /query` (use `type=query` for DDL/DML) |
| `call_endpoint` | any request against the runtime (`R2_RUNTIME_URL`) |

## Configure

Environment: `R2_CONSOLE_URL` (default `http://localhost:7000/console`), `R2_RUNTIME_URL` (default `http://localhost:7000/api`), `R2_USER` / `R2_PASSWORD` (default `admin`/`admin`), optional `R2_TOKEN`.

```json
{ "mcpServers": { "r2-dev": { "command": "node", "args": ["D:/projects/R2/mcp/server.mjs"] } } }
```

Or: `claude mcp add r2-dev -- node D:/projects/R2/mcp/server.mjs`

## Status

Verified against a running Dev server (HSQLDB and PostgreSQL): login, create_app, run_sql, define_resource + hot-deploy, call_endpoint (CRUD). Resources are served at `{R2_RUNTIME_URL}/{app}/v1.0/{name}`. Not yet verified: `upload_script` (Kotlin steps). See `test-log.md`.
