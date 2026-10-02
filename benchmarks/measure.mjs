// Compares the R2 shop (app `benchp` on PostgreSQL, built by mcp/scenarios/shop/run.mjs) with the Spring Boot shop.
// Both must be running: R2 on :7000, Spring on :8081 (see benchmarks/README.md).
import fs from 'node:fs';
import path from 'node:path';
import { execFileSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';
import { startMcp } from '../mcp/scenarios/lib.mjs';

const here = path.dirname(fileURLToPath(import.meta.url));
const root = path.join(here, '..');
const R2 = process.argv[2] || 'benchp';
const out = {};

// ---------- authored size (what an agent has to write) ----------
const size = (files) => {
  let chars = 0, lines = 0;
  for (const f of files) {
    const t = fs.readFileSync(f, 'utf8');
    chars += t.length;
    lines += t.split('\n').length;
  }
  return { files: files.length, lines, chars, approxTokens: Math.round(chars / 4) };
};
const ls = (d, skip = []) => fs.readdirSync(d).filter((f) => !skip.includes(f)).map((f) => path.join(d, f));
const shop = path.join(root, 'mcp', 'scenarios', 'shop');
// R2: schema + scripts + resources (legacy.* only exists to test Groovy compatibility, Spring has no counterpart)
out.authored_r2 = size([path.join(shop, 'schema.sql'), ...ls(path.join(shop, 'scripts'), ['legacy.groovy']), ...ls(path.join(shop, 'resources'), ['legacy.xml'])]);
const sp = path.join(here, 'spring-shop');
out.authored_spring = size([path.join(sp, 'pom.xml'), path.join(sp, 'src/main/resources/application.properties'), path.join(sp, 'src/main/resources/schema.sql'), path.join(sp, 'src/main/java/shop/ShopApplication.java')]);

// ---------- steady-state latency ----------
async function latency(url, init, n = 200) {
  const ts = [];
  for (let i = 0; i < n; i++) {
    const t0 = process.hrtime.bigint();
    const res = await fetch(url, init);
    await res.text();
    ts.push(Number(process.hrtime.bigint() - t0) / 1e6);
  }
  ts.sort((a, b) => a - b);
  return { median_ms: +ts[Math.floor(n / 2)].toFixed(1), p95_ms: +ts[Math.floor(n * 0.95)].toFixed(1) };
}
const r2 = `http://localhost:7000/${R2}/v1.0`;
const spring = 'http://localhost:8081/v1.0';
out.latency_get_item_join = { r2: await latency(`${r2}/order/1`), spring: await latency(`${spring}/order/1`) };
out.latency_get_script_only = { r2: await latency(`${r2}/token/hello`), spring: await latency(`${spring}/token/hello`) };
out.latency_post_order = {
  r2: await latency(`${r2}/order`, { method: 'POST', body: new URLSearchParams({ customer_id: '1', product_id: '2', qty: '1' }) }, 100),
  spring: await latency(`${spring}/order`, { method: 'POST', body: new URLSearchParams({ customer_id: '1', product_id: '2', qty: '1' }) }, 100),
};

// ---------- iteration time on R2: edit -> serving the change ----------
const mcp = startMcp();
await mcp.init();
const timed = async (fn) => {
  const t0 = Date.now();
  const r = await fn();
  return { ms: Date.now() - t0, r };
};
const script = fs.readFileSync(path.join(shop, 'scripts', 'tokens.kts'), 'utf8');
const iter = [];
for (let i = 1; i <= 3; i++) {
  const v = script.replace('response["id"] = id', `response["id"] = id\n    response["rev"] = ${i}`);
  const t = await timed(async () => {
    await mcp.call('upload_script', { app: R2, filename: 'tokens.kts', content: v });
    return mcp.call('call_endpoint', { method: 'GET', path: `/${R2}/v1.0/token/hello` });
  });
  iter.push({ edit: `script rev ${i}`, ms: t.ms, applied: t.r.json?.digest?.rev === i });
}
const orderXml = fs.readFileSync(path.join(shop, 'resources', 'order.xml'), 'utf8').replaceAll('{{app}}', R2).replaceAll('{{runtime}}', 'http://localhost:7000');
for (let i = 1; i <= 2; i++) {
  const v = orderXml.replace('<Desc>', `<Desc>rev${i} `);
  const t = await timed(async () => {
    await mcp.call('define_resource', { app: R2, name: 'order', xml: v });
    return mcp.call('call_endpoint', { method: 'GET', path: `/${R2}/v1.0/order/1` });
  });
  iter.push({ edit: `resource xml rev ${i} (save + validate SQL + deploy)`, ms: t.ms, applied: t.r.status === 200 });
}
out.iteration_r2 = iter;
mcp.close();

// ---------- memory ----------
function rss(match) {
  const ps = `Get-CimInstance Win32_Process -Filter "Name='java.exe'" | Where-Object CommandLine -like '*${match}*' | ForEach-Object { [math]::Round($_.WorkingSetSize/1MB) }`;
  return Number(execFileSync('powershell', ['-NoProfile', '-Command', ps]).toString().trim().split(/\s+/)[0]);
}
out.memory_mb = { r2_tomcat_all_apps: rss('catalina'), spring: rss('spring-shop') };

console.log(JSON.stringify(out, null, 2));
