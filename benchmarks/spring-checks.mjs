// The same behaviours that mcp/scenarios/shop/run.mjs verifies for R2, checked over plain HTTP against the Spring Boot app.
// usage: node benchmarks/spring-checks.mjs [baseUrl] [psql path]
import { execFileSync } from 'node:child_process';
import { createHash } from 'node:crypto';

const base = (process.argv[2] || 'http://localhost:8081') + '/v1.0';
const psql = process.argv[3] || 'D:/projects/R2/mcp/pgsql/bin/psql.exe';
let passed = 0;
const failures = [];
const check = (name, cond, detail = '') => {
  if (cond) {
    passed++;
    console.log(`  ok   ${name}`);
  } else {
    failures.push(name);
    console.log(`  FAIL ${name} :: ${String(detail).slice(0, 200)}`);
  }
};
const db = (sql) => execFileSync(psql, ['-U', 'postgres', '-d', 'springshop', '-Atc', sql]).toString().trim();
async function req(method, path, { query, form } = {}) {
  const qs = query ? '?' + new URLSearchParams(query) : '';
  const init = { method };
  if (form) {
    init.body = new URLSearchParams(form);
    init.headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
  }
  const res = await fetch(base + path + qs, init);
  const text = await res.text();
  let json;
  try {
    json = JSON.parse(text);
  } catch {
    /* not json */
  }
  return { status: res.status, text, json };
}

console.log('== customer ==');
const reg = await req('POST', '/customer', { form: { name: 'Ada', email: 'ada@example.com', password: 'secret' } });
check('register returns 201', reg.status === 201, reg.text);
check('register returns the created row', reg.text.includes('ada@example.com'), reg.text);
const hash = db("SELECT pw_hash FROM customer WHERE email = 'ada@example.com'");
check('password stored as salted sha-256 (salt$hex), not plaintext', /^[0-9a-f]{8}\$[0-9a-f]{64}$/.test(hash) && !hash.includes('secret'), hash);
const dup = await req('POST', '/customer', { form: { name: 'Ada2', email: 'ada@example.com', password: 'x' } });
check('duplicate email is rejected (not 2xx)', dup.status >= 400, dup.text);
await req('POST', '/customer', { form: { name: 'Grace', email: 'grace@example.com', password: 'p2' } });
await req('POST', '/customer', { form: { name: 'Linus', email: 'linus@example.com', password: 'p3' } });
const all = await req('GET', '/customer');
check('collection lists all 3', all.json?.length === 3, all.text);
const recent = await req('GET', '/customer', { query: { q: 'recent' } });
check('?q=recent returns the 2 newest', recent.json?.length === 2, recent.text);
const one = await req('GET', '/customer/1');
check('item request returns customer 1', one.json?.email === 'ada@example.com', one.text);
const none = await req('GET', '/customer/999');
check('missing item returns nothing (404)', none.status === 404, none.text);
const ada = Number(db("SELECT id FROM customer WHERE email = 'ada@example.com'"));

console.log('== order ==');
const o1 = await req('POST', '/order', { form: { customer_id: String(ada), product_id: '1', qty: '3' } });
check('place order returns 201', o1.status === 201, o1.text);
check('priced from the product lookup (3 x 4.50 = 13.5)', Number(o1.json?.total) === 13.5, o1.text);
check('HMAC token (40 hex)', /^[0-9a-f]{40}$/.test(o1.json?.token || ''), o1.text);
check('response contains the placed order joined with the customer', o1.text.includes('Ada') && o1.text.includes('NEW'), o1.text);
check('transaction decremented stock 100 -> 97', (await req('GET', '/product/1')).json?.stock === 97);
check('transaction wrote the order line', db('SELECT COUNT(*) FROM order_line') === '1');
const bad = await req('POST', '/order', { form: { customer_id: String(ada), product_id: '1', qty: '1000' } });
check('insufficient stock is rejected', bad.status >= 400 && bad.text.includes('insufficient stock'), bad.text);
check('stock unchanged after the rejected order', (await req('GET', '/product/1')).json?.stock === 97);
const unk = await req('POST', '/order', { form: { customer_id: String(ada), product_id: '999', qty: '1' } });
check('unknown product is rejected', unk.status >= 400 && unk.text.includes('unknown product'), unk.text);
const orderId = o1.json?.placed?.[0]?.id ?? 1;
const item = await req('GET', `/order/${orderId}`);
check('item joins order, customer and lines', item.text.includes('Ada') && item.text.includes('Notebook') && item.json?.lines?.length === 1, item.text);
check('?customer_id filter', (await req('GET', '/order', { query: { customer_id: String(ada) } })).json?.length === 1);
check('?status filter (none PAID yet)', (await req('GET', '/order', { query: { status: 'PAID' } })).json?.length === 0);
check('no filter returns all orders', (await req('GET', '/order')).json?.length === 1);
const ship = await req('PUT', `/order/${orderId}`, { form: { status: 'SHIPPED' } });
check('NEW -> SHIPPED refused by the state machine', ship.status >= 400 && ship.text.includes('cannot go from NEW to SHIPPED'), ship.text);
const pay = await req('PUT', `/order/${orderId}`, { form: { status: 'PAID' } });
check('NEW -> PAID accepted with 202', pay.status === 202, pay.text);
check('status is now PAID', (await req('GET', `/order/${orderId}`)).text.includes('PAID'));

console.log('== token ==');
const t1 = await req('GET', '/token/hello');
check('item: sha256, md5, base64, uuid', t1.json?.sha256 === createHash('sha256').update('hello').digest('hex') && t1.json?.md5 === createHash('md5').update('hello').digest('hex') && t1.json?.base64 === Buffer.from('hello').toString('base64') && /^[0-9a-f-]{36}$/.test(t1.json?.uuid || ''), t1.text);
check('item is deterministic', (await req('GET', '/token/hello')).json?.uuid === t1.json?.uuid);
check('collection: 5 distinct uuids', new Set((await req('GET', '/token', { query: { count: '5' } })).json?.items || []).size === 5);

console.log('== failure semantics ==');
const before = db("SELECT COUNT(*) FROM orders WHERE status = 'TX'");
const tx = await req('POST', '/txtest', { form: { customer_id: String(ada) } });
check('transaction with a failing statement returns an error', tx.status >= 400, tx.text);
check('...and the first statement was rolled back', db("SELECT COUNT(*) FROM orders WHERE status = 'TX'") === before && before === '0');
const t0 = Date.now();
const xf = await req('GET', '/xfail');
check('external API 500: request completes quickly', xf.status === 200 && Date.now() - t0 < 15000, `${xf.status} ${Date.now() - t0} ms`);

console.log(`\n${passed} passed, ${failures.length} failed${failures.length ? ': ' + failures.join('; ') : ''}`);
process.exit(failures.length ? 1 : 0);
