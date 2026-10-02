# R2
R2 is an open source REST Resource server. It comes with a ready to use REST API. Developer can add/edit resources to the server
using R2 Console

![Metamug Resource Screen](https://metamug.com/img/res-screen.png)

## Dev server quick start

Needs a JDK (8 to 17). Maven is bundled in `server/maven`.

```
dev build     # build parser + console from source into server/
dev start     # http://localhost:7000/console  (login admin/admin)
dev stop
```

On Linux/macOS/Git Bash use `./dev.sh` with the same commands.

`dev mason` builds [Mason](https://github.com/metamug/mason) (the runtime that apps run on) from a checkout (`MASON_HOME`, default `../mason`) with Maven and puts the jar into the app template; run `dev build` afterwards. The template currently embeds a Mason build that includes the open Mason PRs (Kotlin runner, error log, pooled tag state, request status, param map, JAXB cache).

### Layout

| Folder | What it is |
|---|---|
| `parser/` | XML resource parser (builds `openapi-rest-model` jar into `server/lib`) |
| `console/` | Console webapp and REST API for apps, resources, deployment (builds `console.war`) |
| `server/` | Tomcat based server. Apps you create are generated into `server/webapps`, `server/databases`, `server/backend` (git-ignored) |
| `mcp/` | MCP server so an AI agent can create apps, define/hot-deploy resources and call them (see `mcp/README.md`) |
| `cli/` | `mtg` command line client |
| `docs/` | Documentation |

### Agent workflow (MCP)

```
claude mcp add r2-dev -- node mcp/server.mjs
```

Then ask the agent to create an app, a table and a resource. `mcp/test-log.md` records the Dev server validation run and the issues it found.

## R2 Console
R2 console comes with following features.

* REST Resource Management 
* Hot deployment for REST Resources
* Resource editor with autocomplete and query testing (using open source project)
* Open API documentation generated.

## API Integration with XRequest

API Integration with third party services like AWS, Facebook, Twitter, Firebase, PayPal, Mailchimp and more.
Communication with multiple services in a single request using API Gateways.

![Metamug API Integration](https://metamug.com/img/api-integration1.svg)


## openapi-rest-model

![](https://travis-ci.org/metamug/openapi-rest-model.svg?branch=open-api) [![codecov](https://codecov.io/gh/metamug/openapi-rest-model/branch/open-api/graph/badge.svg)](https://codecov.io/gh/metamug/openapi-rest-model)

![Metamug Open API Integration](https://metamug.com/img/openapi-specification.svg)


Convert [OpenAPI](https://www.openapis.org/) Spec file into [Resource Resource XML](https://metamug.com/docs/resource-file) files.
This project aims to generate compatible resource xmls for a given spec file json/yml.

The sample petstore yml file will convert to a number of resource xmls.
https://editor.swagger.io/

### REST Resource Files

REST Resource files are designed to address a REST resources individually. Each Resource file
maps to its own URI. This makes resources easily identifiable and managable.

https://metamug.com/docs/resource-file

### Datasources

[Read this](https://github.com/metamug/R2/blob/develop/docs/datasources.md)

### Open API 

https://github.com/OAI/OpenAPI-Specification

## Dependencies

[Mason](https://github.com/metamug/mason) v3.4 available on Maven central
