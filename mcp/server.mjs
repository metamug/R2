#!/usr/bin/env node
// Minimal dependency-free MCP server (stdio, JSON-RPC 2.0) wrapping the R2 Console HTTP API.
//
// Env:
//   R2_CONSOLE_URL  Console base URL      (default http://localhost:7000/console)
//   R2_RUNTIME_URL  base URL of deployed resources for call_endpoint (default http://localhost:7000; resources live at {base}/{app}/v1.0/{name})
//   R2_USER / R2_PASSWORD  Console credentials (default admin/admin)
//   R2_TOKEN        optional pre-issued token (skips login)
import fs from 'node:fs';
import path from 'node:path';
import readline from 'node:readline';
import { fileURLToPath } from 'node:url';

const CONSOLE = (process.env.R2_CONSOLE_URL || 'http://localhost:7000/console').replace(/\/$/, '');
const RUNTIME = (process.env.R2_RUNTIME_URL || 'http://localhost:7000').replace(/\/$/, '');
let token = process.env.R2_TOKEN || '';

async function login() {
  const user = process.env.R2_USER || 'admin';
  const pass = process.env.R2_PASSWORD || 'admin';
  // Console expects a base64 password; the CLI strips "==" padding, mirror that.
  const encoded = Buffer.from(pass).toString('base64').split('==')[0];
  let last = '';
  for (let attempt = 0; attempt < 3; attempt++) {
    const res = await fetch(`${CONSOLE}/accesstoken`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ username: user, password: encoded }),
    });
    const body = await res.json().catch(() => ({}));
    if (res.ok && body.token) {
      token = body.token;
      return;
    }
    last = `login failed (${res.status}): ${JSON.stringify(body)}`;
    await new Promise((r) => setTimeout(r, 1000)); // the Console occasionally answers 401 once under load
  }
  throw new Error(last);
}

async function consoleReq(method, path, { form, rawBody, contentType } = {}, retried = false) {
  if (!token) await login();
  const headers = { Authorization: token };
  let body;
  if (form) {
    body = new URLSearchParams(form);
    headers['Content-Type'] = 'application/x-www-form-urlencoded';
  } else if (rawBody !== undefined) {
    body = rawBody;
    headers['Content-Type'] = contentType || 'application/xml';
  }
  const res = await fetch(`${CONSOLE}${path}`, { method, headers, body });
  if (res.status === 401 && !retried && !process.env.R2_TOKEN) {
    token = '';
    return consoleReq(method, path, { form, rawBody, contentType }, true);
  }
  return { status: res.status, body: await res.text() };
}

const enc = encodeURIComponent;
const fmt = (r) => `HTTP ${r.status}\n${r.body}`;

const tools = [
  {
    name: 'list_apps',
    description: 'List backends (apps) available on the Dev server.',
    inputSchema: { type: 'object', properties: {} },
    run: async () => fmt(await consoleReq('GET', '/app')),
  },
  {
    name: 'create_app',
    description: 'Create a backend. Defaults to the internal HSQLDB; pass dbtype/dburl/dbuser/dbpass for an external database such as PostgreSQL.',
    inputSchema: {
      type: 'object',
      properties: {
        id: { type: 'string', description: 'Backend name' },
        desc: { type: 'string' },
        dbtype: { type: 'string', description: 'internal (default), mysql, postgresql, ...' },
        dburl: { type: 'string' },
        dbuser: { type: 'string' },
        dbpass: { type: 'string' },
      },
      required: ['id', 'desc'],
    },
    run: async ({ id, desc, dbtype, dburl, dbuser, dbpass }) => {
      const form = { id, desc };
      for (const [k, v] of Object.entries({ dbtype, dburl, dbuser, dbpass })) if (v !== undefined) form[k] = v;
      return fmt(await consoleReq('POST', '/app', { form }));
    },
  },
  {
    name: 'delete_app',
    description: 'Delete a backend and its webapp. Wait before re-creating the same name (a quick re-create can leave a half-extracted app).',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => fmt(await consoleReq('DELETE', `/app/${enc(app)}`)),
  },
  {
    name: 'list_scripts',
    description: 'List the script files of a backend.',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => fmt(await consoleReq('GET', `/app/${enc(app)}/script`)),
  },
  {
    name: 'get_script',
    description: 'Read the source of a script (name without extension).',
    inputSchema: { type: 'object', properties: { app: { type: 'string' }, name: { type: 'string' } }, required: ['app', 'name'] },
    run: async ({ app, name }) => fmt(await consoleReq('GET', `/app/${enc(app)}/script/${enc(name.replace(/\.(kts|groovy)$/, ''))}`)),
  },
  {
    name: 'delete_script',
    description: 'Delete a script (name without extension).',
    inputSchema: { type: 'object', properties: { app: { type: 'string' }, name: { type: 'string' } }, required: ['app', 'name'] },
    run: async ({ app, name }) => fmt(await consoleReq('DELETE', `/app/${enc(app)}/script/${enc(name.replace(/\.(kts|groovy)$/, ''))}`)),
  },
  {
    name: 'get_stats',
    description: 'Usage summary of a backend: requests per resource and version, and error counts (compact).',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => {
      const r = await consoleReq('GET', `/stat/${enc(app)}`);
      if (r.status !== 200) return fmt(r);
      try {
        const j = JSON.parse(r.body);
        const keep = {};
        for (const k of ['resourcecount', 'errorcount', 'statuscount', 'methodcount']) if (j[k]) keep[k] = j[k];
        return JSON.stringify(Object.keys(keep).length ? keep : j);
      } catch {
        return fmt(r);
      }
    },
  },
  {
    name: 'wait_for_app',
    description:
      'Wait until a newly created backend is actually serving (create_app returns before its webapp is deployed, usually 10-40 s). ' +
      'Call this after create_app and before run_sql / define_resource.',
    inputSchema: {
      type: 'object',
      properties: { app: { type: 'string' }, timeoutSeconds: { type: 'number', description: 'default 120' } },
      required: ['app'],
    },
    run: async ({ app, timeoutSeconds = 120 }) => {
      const t0 = Date.now();
      while (Date.now() - t0 < timeoutSeconds * 1000) {
        try {
          const res = await fetch(`${RUNTIME}/${enc(app)}/`);
          if (res.status === 200) return `HTTP 200 (ready after ${Math.round((Date.now() - t0) / 1000)} s)`;
        } catch {
          /* not up yet */
        }
        await new Promise((r) => setTimeout(r, 1500));
      }
      return `HTTP 504\nbackend ${app} not serving after ${timeoutSeconds} s`;
    },
  },
  {
    name: 'run_sql',
    description: 'Run SQL (DDL/DML/query) against a backend database, e.g. to create the tables a resource uses. Use type "query" for any SQL including DDL/DML ("plsql" is only for functions/procedures).',
    inputSchema: {
      type: 'object',
      properties: { app: { type: 'string' }, sql: { type: 'string' }, type: { type: 'string', enum: ['query', 'plsql'] } },
      required: ['app', 'sql', 'type'],
    },
    run: async ({ app, sql, type }) => fmt(await consoleReq('POST', '/query', { form: { appid: app, sql, type } })),
  },
  {
    name: 'list_resources',
    description: 'List resource files (name, version, deployed flag) of a backend.',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => fmt(await consoleReq('GET', `/app/${enc(app)}/rpx`)),
  },
  {
    name: 'get_resource',
    description: 'Download the XML of a saved resource file.',
    inputSchema: {
      type: 'object',
      properties: { app: { type: 'string' }, name: { type: 'string' }, version: { type: 'string', description: 'e.g. v1.0' } },
      required: ['app', 'name', 'version'],
    },
    run: async ({ app, name, version }) => fmt(await consoleReq('GET', `/app/${enc(app)}/rpx/${enc(version)}/${enc(name)}`)),
  },
  {
    name: 'define_resource',
    description:
      'Create or update a resource from its XML definition. Name must be letters only. If the resource already exists it is updated in place. ' +
      'Set deploy=true (default) to hot-deploy the backend afterwards. Validation errors come back as HTTP 422 with a message.',
    inputSchema: {
      type: 'object',
      properties: {
        app: { type: 'string' },
        name: { type: 'string', description: 'Resource file name, letters only, with or without .xml' },
        xml: { type: 'string', description: 'Full <Resource> XML document' },
        deploy: { type: 'boolean', description: 'Hot-deploy the backend after saving (default true)' },
      },
      required: ['app', 'name', 'xml'],
    },
    run: async ({ app, name, xml, deploy = true }) => {
      const file = name.endsWith('.xml') ? name : `${name}.xml`;
      let r = await consoleReq('POST', `/app/${enc(app)}/rpx`, { form: { filename: file, data: xml } });
      let action = 'created';
      if (r.status === 409) {
        const v = /<Resource[^>]*\sv="([^"]+)"/.exec(xml)?.[1] || '1.0';
        r = await consoleReq('PUT', `/app/${enc(app)}/rpx/v${enc(v)}/${enc(file.replace(/\.xml$/, ''))}`, { rawBody: xml });
        action = 'updated';
      }
      let out = `${action}: ${fmt(r)}`;
      if (deploy && r.status < 300) {
        out += `\n\ndeploy: ${fmt(await consoleReq('POST', `/app/${enc(app)}/deployment`, { form: { backend: app } }))}`;
      }
      return out;
    },
  },
  {
    name: 'delete_resource',
    description: 'Delete a resource file.',
    inputSchema: {
      type: 'object',
      properties: { app: { type: 'string' }, name: { type: 'string' }, version: { type: 'string', description: 'e.g. v1.0' } },
      required: ['app', 'name', 'version'],
    },
    run: async ({ app, name, version }) => fmt(await consoleReq('DELETE', `/app/${enc(app)}/rpx/${enc(version)}/${enc(name)}`)),
  },
  {
    name: 'deploy_backend',
    description: 'Hot-deploy (load) all resources of a backend. No server restart.',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => fmt(await consoleReq('POST', `/app/${enc(app)}/deployment`, { form: { backend: app } })),
  },
  {
    name: 'deployment_status',
    description: 'Check whether a backend is currently deployed.',
    inputSchema: { type: 'object', properties: { app: { type: 'string' } }, required: ['app'] },
    run: async ({ app }) => fmt(await consoleReq('GET', `/app/${enc(app)}/deployment?backend=${enc(app)}`)),
  },
  {
    name: 'upload_script',
    description: 'Create or update a script referenced by a <Script file="name"/> step. Kotlin (.kts) is the default language; a .groovy name keeps using Groovy. Scripts see params (Map), request (id, body, method), response (MutableMap, the step output) and ds (DataSource). Updates are hot.',
    inputSchema: {
      type: 'object',
      properties: { app: { type: 'string' }, filename: { type: 'string' }, content: { type: 'string' } },
      required: ['app', 'filename', 'content'],
    },
    run: async ({ app, filename, content }) => {
      let r = await consoleReq('POST', `/app/${enc(app)}/script`, { form: { filename, data: content } });
      if (r.status === 409) {
        // already exists: update in place (hot, picked up on the next request)
        r = await consoleReq('PUT', `/app/${enc(app)}/script/${enc(filename)}`, { rawBody: content, contentType: 'text/plain' });
        return `updated: ${fmt(r)}`;
      }
      return `created: ${fmt(r)}`;
    },
  },
  {
    name: 'get_errors',
    description:
      'Recent runtime errors of a backend from the Console error log (the cause behind an HTTP 512 "errorId"). ' +
      'Pass errorId to fetch one. Returns message, resource uri and the top of the stack trace; set trace=true for the full trace.',
    inputSchema: {
      type: 'object',
      properties: {
        app: { type: 'string' },
        errorId: { type: 'string', description: 'errorId from a 512 response' },
        limit: { type: 'number', description: 'max errors, newest first (default 5)' },
        trace: { type: 'boolean', description: 'include the full stack trace (default: first 3 frames)' },
      },
      required: ['app'],
    },
    run: async ({ app, errorId, limit = 5, trace = false }) => {
      const r = await consoleReq('GET', `/error/${enc(app)}`);
      if (r.status !== 200) return fmt(r);
      let rows;
      try {
        rows = JSON.parse(r.body).data || [];
      } catch {
        return fmt(r);
      }
      if (errorId) rows = rows.filter((e) => String(e.error_id) === String(errorId));
      rows = rows.slice(0, limit).map((e) => ({
        error_id: e.error_id,
        method: e.method,
        uri: e.uri,
        created_on: e.created_on,
        message: e.message,
        trace: trace ? e.trace : String(e.trace || '').split('\n').slice(0, 3).join('\n'),
      }));
      return rows.length ? JSON.stringify(rows, null, 1) : 'no matching errors';
    },
  },
  {
    name: 'call_endpoint',
    description: 'Call a deployed REST endpoint on the runtime to verify behaviour. Path is relative to R2_RUNTIME_URL, e.g. /myapp/v1.0/movie/1.',
    inputSchema: {
      type: 'object',
      properties: {
        method: { type: 'string', enum: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH'] },
        path: { type: 'string' },
        query: { type: 'object', additionalProperties: { type: 'string' } },
        body: { type: 'object', description: 'Sent as form-encoded parameters (the runtime reads request parameters)' },
        json: { type: 'string', description: 'Raw JSON body; overrides body' },
        headers: { type: 'object', additionalProperties: { type: 'string' } },
      },
      required: ['method', 'path'],
    },
    run: async ({ method, path, query, body, json, headers = {} }) => {
      const qs = query ? `?${new URLSearchParams(query)}` : '';
      const init = { method, headers: { ...headers } };
      if (json !== undefined) {
        init.body = json;
        init.headers['Content-Type'] ||= 'application/json';
      } else if (body) {
        init.body = new URLSearchParams(body);
        init.headers['Content-Type'] ||= 'application/x-www-form-urlencoded';
      }
      const t0 = Date.now();
      const res = await fetch(`${RUNTIME}${path.startsWith('/') ? '' : '/'}${path}${qs}`, init);
      const text = await res.text();
      const hint = /"errorId"/.test(text) ? '\n(runtime error: call get_errors with this errorId for the cause)' : '';
      return `HTTP ${res.status} (${Date.now() - t0} ms)\n${text.slice(0, 4000)}${hint}`;
    },
  },
];

const byName = new Map(tools.map((t) => [t.name, t]));

// MCP resources: the authoring guide, the repo docs and the worked shop example, so an agent can read
// what it needs on demand instead of exploring the source tree.
const HERE = path.dirname(fileURLToPath(import.meta.url));
const resourceFiles = [];
function addResource(uri, file, description) {
  if (fs.existsSync(file)) resourceFiles.push({ uri, name: uri.replace('r2://', ''), description, mimeType: 'text/markdown', file });
}
addResource('r2://guide', path.join(HERE, 'guide.md'), 'Start here: R2 authoring guide (workflow, resource XML, mpath, Kotlin scripts, gotchas)');
for (const [name, description] of [
  ['resource-file', 'Resource XML reference'],
  ['mpath', 'MPath expressions between steps'],
  ['xrequest', 'XRequest: calling external APIs'],
  ['scripting', 'Scripting'],
  ['request-parameters', 'Request parameters'],
  ['output-format', 'Response output format'],
  ['auth', 'Authentication and roles'],
]) {
  addResource(`r2://docs/${name}`, path.join(HERE, '..', 'docs', 'markdown', `${name}.md`), description);
}
const shop = path.join(HERE, 'scenarios', 'shop');
for (const sub of ['resources', 'scripts']) {
  if (fs.existsSync(path.join(shop, sub))) {
    for (const f of fs.readdirSync(path.join(shop, sub))) addResource(`r2://examples/shop/${sub}/${f}`, path.join(shop, sub, f), 'Worked example (shop scenario, tested)');
  }
}

const send = (msg) => process.stdout.write(JSON.stringify({ jsonrpc: '2.0', ...msg }) + '\n');

async function handle({ id, method, params }) {
  try {
    switch (method) {
      case 'initialize':
        return send({
          id,
          result: {
            protocolVersion: params?.protocolVersion || '2024-11-05',
            capabilities: { tools: {}, resources: {} },
            serverInfo: { name: 'r2-dev-mcp', version: '0.1.0' },
          },
        });
      case 'ping':
        return send({ id, result: {} });
      case 'resources/list':
        return send({ id, result: { resources: resourceFiles.map(({ uri, name, description, mimeType }) => ({ uri, name, description, mimeType })) } });
      case 'resources/read': {
        const res = resourceFiles.find((r) => r.uri === params?.uri);
        if (!res) return send({ id, error: { code: -32602, message: `unknown resource ${params?.uri}` } });
        return send({ id, result: { contents: [{ uri: res.uri, mimeType: res.mimeType, text: fs.readFileSync(res.file, 'utf8') }] } });
      }
      case 'tools/list':
        return send({ id, result: { tools: tools.map(({ name, description, inputSchema }) => ({ name, description, inputSchema })) } });
      case 'tools/call': {
        const tool = byName.get(params?.name);
        if (!tool) return send({ id, error: { code: -32602, message: `unknown tool ${params?.name}` } });
        try {
          const text = await tool.run(params.arguments || {});
          return send({ id, result: { content: [{ type: 'text', text }] } });
        } catch (e) {
          return send({ id, result: { isError: true, content: [{ type: 'text', text: String(e.message || e) }] } });
        }
      }
      default:
        if (id !== undefined) send({ id, error: { code: -32601, message: `method not found: ${method}` } });
    }
  } catch (e) {
    if (id !== undefined) send({ id, error: { code: -32603, message: String(e) } });
  }
}

readline.createInterface({ input: process.stdin }).on('line', (line) => {
  if (!line.trim()) return;
  let req;
  try {
    req = JSON.parse(line);
  } catch {
    return;
  }
  handle(req);
});
