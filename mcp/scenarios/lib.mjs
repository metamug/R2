// Minimal helpers for MCP scenario tests: talk to mcp/server.mjs over stdio and assert on results.
import { spawn } from 'node:child_process';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const here = path.dirname(fileURLToPath(import.meta.url));

export function startMcp(env = {}) {
  const p = spawn('node', [path.join(here, '..', 'server.mjs')], { env: { ...process.env, ...env } });
  let buf = '';
  const waiters = new Map();
  let id = 0;
  p.stdout.on('data', (d) => {
    buf += d;
    let i;
    while ((i = buf.indexOf('\n')) >= 0) {
      const line = buf.slice(0, i);
      buf = buf.slice(i + 1);
      if (!line.trim()) continue;
      const m = JSON.parse(line);
      waiters.get(m.id)?.(m);
    }
  });
  const rpc = (method, params) =>
    new Promise((resolve) => {
      const n = ++id;
      waiters.set(n, resolve);
      p.stdin.write(JSON.stringify({ jsonrpc: '2.0', id: n, method, params }) + '\n');
    });
  return {
    init: () => rpc('initialize', {}),
    tools: async () => (await rpc('tools/list', {})).result.tools,
    /** calls an MCP tool and returns { status, body, text } parsed from the "HTTP <n>\n<body>" convention */
    async call(name, args = {}) {
      const r = await rpc('tools/call', { name, arguments: args });
      const text = r.result.content[0].text;
      const m = /HTTP (\d+)(?: \((\d+) ms\))?\n?([\s\S]*)/.exec(text);
      const body = m ? m[3] : text;
      let json;
      try {
        json = JSON.parse(body);
      } catch {
        /* not json */
      }
      return { status: m ? Number(m[1]) : undefined, ms: m && m[2] ? Number(m[2]) : undefined, body, json, text, isError: !!r.result.isError };
    },
    close: () => p.kill(),
  };
}

export const read = (...parts) => fs.readFileSync(path.join(...parts), 'utf8');

let passed = 0;
let failed = 0;
const failures = [];

export function check(name, cond, detail = '') {
  if (cond) {
    passed++;
    console.log(`  ok   ${name}`);
  } else {
    failed++;
    failures.push(name);
    console.log(`  FAIL ${name}${detail ? ' :: ' + String(detail).slice(0, 300) : ''}`);
  }
}

export function summary() {
  console.log(`\n${passed} passed, ${failed} failed${failed ? ': ' + failures.join('; ') : ''}`);
  return failed === 0;
}

export const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

/** polls until fn returns truthy (or throws away errors) */
export async function waitFor(fn, { tries = 40, delay = 1500, label = 'condition' } = {}) {
  for (let i = 0; i < tries; i++) {
    try {
      const v = await fn();
      if (v) return v;
    } catch {
      /* retry */
    }
    await sleep(delay);
  }
  throw new Error(`timed out waiting for ${label}`);
}
