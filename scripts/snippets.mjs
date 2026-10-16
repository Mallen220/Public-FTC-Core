// Checks the snippet packs, builds snippets/dist/index.json and writes docs/snippets.md.
//   node scripts/snippets.mjs validate
//   node scripts/snippets.mjs build
//
// A pack is a folder in snippets/packs. Its name is the pack id. The folder holds one pack.json and a
// .java file per snippet, named after the snippet's prefix. See CONTRIBUTING.md for the file format.
import Ajv from 'ajv';
import { existsSync, readdirSync, readFileSync, writeFileSync } from 'fs';
import { dirname, join, relative } from 'path';
import { fileURLToPath } from 'url';

const root = join(dirname(fileURLToPath(import.meta.url)), '..');
const schemaDir = join(root, 'snippets', 'schema');
const schema = JSON.parse(readFileSync(join(schemaDir, 'snippet-pack.schema.json'), 'utf8'));
const metadataSchema = JSON.parse(readFileSync(join(schemaDir, 'pack-metadata.schema.json'), 'utf8'));
const packDir = join(root, 'snippets', 'packs');

const HEADER_LINE = /^\/\/\s*(Title|Description):\s*(.*?)\s*$/;
const IMPORT_LINE = /^import\s+(static\s+)?([\w.]+);\s*$/;
// A top-level class named after the file is what makes a snippet a whole file.
const WHOLE_FILE = /^(?:(?:public|final|abstract)\s+)*(?:class|interface|enum|record)\s+\$\{TM_FILENAME_BASE\}/;

/**
 * Reads one snippet file: `// Title:` and an optional `// Description:` at the top, then the imports,
 * then the code that gets inserted.
 */
export function parseSnippet(text) {
  const problems = [];
  const lines = text.replace(/^﻿/, '').replace(/\r\n?/g, '\n').split('\n');
  const header = {};
  let i = 0;
  for (; i < lines.length; i++) {
    const match = HEADER_LINE.exec(lines[i]);
    if (!match) {
      break;
    }
    const [, key, value] = match;
    if (key in header) {
      problems.push(`line ${i + 1}: there are two "// ${key}:" lines`);
    } else if (value === '') {
      problems.push(
        `line ${i + 1}: the "// ${key}:" line is empty${key === 'Description' ? ', so give it text or delete it' : ''}`,
      );
    }
    header[key] = value;
  }
  if (!('Title' in header)) {
    problems.push('the first line must be "// Title: " followed by the name shown in the snippet list');
  }
  const imports = [];
  for (; i < lines.length; i++) {
    if (lines[i].trim() === '') {
      continue;
    }
    const match = IMPORT_LINE.exec(lines[i]);
    if (!match) {
      break;
    }
    imports.push(`${match[1] ? 'static ' : ''}${match[2]}`);
  }
  const start = i;
  const body = lines.slice(start);
  while (body.length > 0 && body[body.length - 1].trim() === '') {
    body.pop();
  }
  if (body.length === 0) {
    problems.push('there is no code after the header and imports');
  }
  body.forEach((line, index) => {
    if (/^import\s/.test(line)) {
      problems.push(
        `line ${start + index + 1}: imports go at the top, before the code, one class per line and no wildcards`,
      );
    }
  });
  return {
    title: header.Title,
    description: header.Description,
    imports,
    body,
    template: body.some((line) => WHOLE_FILE.test(line)),
    problems,
  };
}

/**
 * Reads every pack folder. A folder with problems is reported and left out, so one mistake doesn't hide
 * the rest. Each entry is the pack as it appears in the built index, plus `sources` (snippet title to
 * source file) so later errors can name the file.
 */
export function loadPacks(dir = packDir) {
  const ajv = new Ajv({ allErrors: true, strict: true, allowUnionTypes: true });
  const validateMetadata = ajv.compile(metadataSchema);
  const entries = [];
  const errors = [];
  const names = readdirSync(dir, { withFileTypes: true })
    .filter((d) => !d.name.startsWith('.'))
    .sort((a, b) => (a.name < b.name ? -1 : 1));
  for (const dirent of names) {
    const folder = join(dir, dirent.name);
    const label = relative(root, folder);
    if (!dirent.isDirectory()) {
      errors.push(
        `${label}: packs are folders. Put pack.json and the .java files in ${label.replace(/\.[^.]*$/, '')}/`,
      );
      continue;
    }
    const before = errors.length;
    const files = readdirSync(folder, { withFileTypes: true })
      .filter((f) => !f.name.startsWith('.'))
      .sort((a, b) => (a.name < b.name ? -1 : 1));
    let meta;
    if (!files.some((f) => f.name === 'pack.json')) {
      errors.push(`${label}: there is no pack.json in this folder`);
    } else {
      try {
        meta = JSON.parse(readFileSync(join(folder, 'pack.json'), 'utf8'));
      } catch (err) {
        errors.push(`${label}/pack.json: ${err.message}`);
      }
    }
    if (meta && !validateMetadata(meta)) {
      for (const error of validateMetadata.errors ?? []) {
        errors.push(`${label}/pack.json: ${error.instancePath || '/'} ${schemaMessage(error)}`);
      }
      meta = undefined;
    }
    const snippets = {};
    const sources = {};
    for (const file of files) {
      if (file.name === 'pack.json') {
        continue;
      }
      const source = `${label}/${file.name}`;
      if (!file.isFile() || !file.name.endsWith('.java')) {
        errors.push(`${source}: only pack.json and .java files belong in a pack folder`);
        continue;
      }
      const prefix = file.name.slice(0, -'.java'.length);
      const parsed = parseSnippet(readFileSync(join(folder, file.name), 'utf8'));
      errors.push(...parsed.problems.map((p) => `${source}: ${p}`));
      if (meta && !prefix.startsWith(meta.prefix)) {
        errors.push(`${source}: the file name is the snippet's prefix, so it must start with ${meta.prefix}`);
      }
      if (parsed.title !== undefined && parsed.title in snippets) {
        errors.push(`${source}: the title "${parsed.title}" is already used by ${sources[parsed.title]}`);
        continue;
      }
      if (parsed.title === undefined) {
        continue;
      }
      sources[parsed.title] = source;
      snippets[parsed.title] = {
        prefix,
        // The built index needs a description, so a snippet without one is described by its title.
        description: parsed.description ?? parsed.title,
        body: parsed.body,
        ...(parsed.imports.length > 0 && { imports: parsed.imports }),
        ...(parsed.template && { template: true }),
      };
    }
    if (meta && Object.keys(snippets).length === 0 && errors.length === before) {
      errors.push(`${label}: the pack has no .java snippet files`);
    }
    if (meta && errors.length === before) {
      const { name, description, appliesTo, prefix, requires } = meta;
      const pack = {
        schemaVersion: 1,
        id: dirent.name,
        name,
        ...(description && { description }),
        ...(appliesTo && { appliesTo }),
        prefix,
        ...(requires && { requires }),
        snippets,
      };
      entries.push({ file: label, pack, sources });
    }
  }
  return { entries, errors };
}

/** A snippet body with every placeholder replaced by its default text. */
export function expandBody(body) {
  let text = body.replace(/\$\{TM_FILENAME_BASE\}/g, 'Name').replace(/\$\{\d+\|([^,|}]*)[^|}]*\|\}/g, '$1');
  for (let previous = ''; previous !== text;) {
    previous = text;
    text = text.replace(/\$\{\d+:([^${}]*)\}/g, '$1').replace(/\$\{\d+\}/g, '');
  }
  return text.replace(/\$\d+/g, '');
}

export function isBalanced(text) {
  const stack = [];
  const pairs = { ')': '(', ']': '[', '}': '{' };
  const code = text
    .replace(/"(?:[^"\\]|\\.)*"/g, '""')
    .replace(/'(?:[^'\\]|\\.)*'/g, "''")
    .replace(/\/\/.*$/gm, '');
  for (const ch of code) {
    if ('([{'.includes(ch)) {
      stack.push(ch);
    } else if (ch in pairs && stack.pop() !== pairs[ch]) {
      return false;
    }
  }
  return stack.length === 0;
}

/** Problems with one pack that the schema can't express. */
export function packProblems(pack, sources = {}) {
  const problems = [];
  for (const [title, snippet] of Object.entries(pack.snippets)) {
    const where = sources[title] ?? `${pack.id}: "${title}"`;
    if (!snippet.prefix.startsWith(pack.prefix)) {
      problems.push(`${where}: prefix ${snippet.prefix} must start with ${pack.prefix}`);
    }
    const text = snippet.body.join('\n');
    if (!isBalanced(expandBody(text))) {
      problems.push(`${where}: brackets in the body don't balance`);
    }
    if ((text.match(/\$0|\$\{0[:}]/g) ?? []).length > 1) {
      problems.push(`${where}: $0 can appear only once`);
    }
    if (snippet.template && !text.includes('${TM_FILENAME_BASE}')) {
      problems.push(`${where}: a whole-file snippet must name its class with \${TM_FILENAME_BASE}`);
    }
    if (/[\u0000-\u0008\u000b-\u001f]/.test(text)) {
      problems.push(`${where}: control characters in the body`);
    }
  }
  return problems;
}

function versionParts(version) {
  return version.split('.').map(Number);
}

function compare(a, b) {
  const [left, right] = [versionParts(a), versionParts(b)];
  for (let i = 0; i < Math.max(left.length, right.length); i++) {
    const diff = (left[i] ?? 0) - (right[i] ?? 0);
    if (diff !== 0) {
      return diff;
    }
  }
  return 0;
}

/** True when no FTC SDK version can be in both packs' ranges. */
function sdkRangesDisjoint(a, b) {
  const [x, y] = [a.requires ?? {}, b.requires ?? {}];
  return (
    (x.sdkMax && y.sdkMin && compare(x.sdkMax, y.sdkMin) <= 0) ||
    (y.sdkMax && x.sdkMin && compare(y.sdkMax, x.sdkMin) <= 0)
  );
}

function schemaMessage(error) {
  const extra = error.params?.additionalProperty;
  return extra ? `${error.message} (${extra})` : error.message;
}

/** Schema error paths look like /snippets/<title>/imports/2. Rewrites one to name the snippet's file. */
function describeSchemaError(error, file, sources) {
  const match = /^\/snippets\/((?:[^/~]|~[01])+)(\/.*)?$/.exec(error.instancePath);
  const title = match?.[1].replace(/~1/g, '/').replace(/~0/g, '~');
  if (title && sources[title]) {
    return `${sources[title]}: ${match[2] ? `${match[2].slice(1)} ` : ''}${schemaMessage(error)}`;
  }
  return `${file}: ${error.instancePath || '/'} ${schemaMessage(error)}`;
}

export function validatePacks(entries, packSchema = schema) {
  const ajv = new Ajv({ allErrors: true, strict: true, allowUnionTypes: true });
  const validate = ajv.compile(packSchema);
  const errors = [];
  const valid = [];
  for (const { file, pack, sources = {} } of entries) {
    if (!validate(pack)) {
      for (const error of validate.errors ?? []) {
        errors.push(describeSchemaError(error, file, sources));
      }
      continue;
    }
    errors.push(...packProblems(pack, sources));
    valid.push(pack);
  }
  // Two packs can be offered together unless their SDK ranges don't overlap, so they can't share a prefix.
  const owners = new Map();
  for (const pack of valid) {
    for (const snippet of Object.values(pack.snippets)) {
      for (const other of owners.get(snippet.prefix) ?? []) {
        if (!sdkRangesDisjoint(other, pack)) {
          errors.push(`${pack.id}: prefix ${snippet.prefix} is also used by ${other.id}`);
        }
      }
      owners.set(snippet.prefix, [...(owners.get(snippet.prefix) ?? []), pack]);
    }
  }
  return errors;
}

export function buildIndex(entries, previous) {
  const packs = entries.map((e) => e.pack);
  // Keep the old date when nothing changed. The extension ignores a downloaded index that looks older
  // than the copy it ships with.
  const unchanged = previous && JSON.stringify(previous.packs) === JSON.stringify(packs);
  return {
    schemaVersion: 1,
    generated: unchanged ? previous.generated : new Date().toISOString().slice(0, 10),
    packs,
  };
}

/**
 * Where a pack goes in the docs, worked out from what it requires so a new pack needs no list to edit:
 * packs for every project, then packs for the FTC SDK itself (an SDK range, no library), then library packs.
 */
function packRank(pack) {
  const { anyOf, allOf, sdkMin, sdkMax } = pack.requires ?? {};
  if (anyOf || allOf) {
    return 2;
  }
  return sdkMin || sdkMax ? 1 : 0;
}

export function sortPacks(packs) {
  return [...packs].sort(
    (a, b) =>
      packRank(a) - packRank(b) ||
      a.name.localeCompare(b.name, 'en', { numeric: true, sensitivity: 'base' }) ||
      a.id.localeCompare(b.id),
  );
}

export function renderDocs(packs) {
  const lines = [
    '# Snippets',
    '',
    '<!-- Generated by `npm run build` from snippets/packs. Edit the packs, not this file. -->',
    '',
    'FTC Core offers code snippets while you type in a Java file. Start typing a prefix such as `ftc_teleop` or',
    '`pedro_teleop` and pick the snippet from the completion list, or run **FTC: Insert Snippet** to browse them.',
    'FTC Core adds the imports a snippet needs. Whole-file snippets (OpModes, subsystems) take the package and the',
    'class name from the file.',
    '',
    "Which snippets you see depends on the project. FTC Core reads the project's dependencies, so a Pedro Pathing",
    "project gets the Pedro snippets and no Road Runner ones, and a SolversLib project gets SolversLib's commands",
    "and not NextFTC's. Projects with no libraries get the FTC SDK snippets only.",
    '',
    "Every prefix starts with the name of its library, so typing `pedro` lists all of that library's snippets. Turn snippets off with the `ftc.snippets.enabled` setting.",
    '',
    'FTC Core downloads the latest snippets from this repository once a day, so a change merged here reaches',
    "everyone without an extension update. If a snippet is wrong or missing for a library version, open an issue",
    'or send a pull request. [CONTRIBUTING.md](../CONTRIBUTING.md#snippets) explains how.',
    '',
  ];
  for (const pack of sortPacks(packs)) {
    lines.push(`## ${pack.name}`, '');
    if (pack.description) {
      lines.push(pack.description, '');
    }
    lines.push(`Offered for: ${pack.appliesTo ?? 'every FTC project.'}`, '', '| Prefix | Inserts |', '| --- | --- |');
    for (const snippet of Object.values(pack.snippets)) {
      lines.push(`| \`${snippet.prefix}\` | ${snippet.description.replace(/\|/g, '\\|')} |`);
    }
    lines.push('');
  }
  return lines.join('\n');
}

function main() {
  const command = process.argv[2] ?? 'validate';
  const { entries, errors: loadErrors } = loadPacks();
  const errors = [...loadErrors, ...validatePacks(entries)];
  if (errors.length > 0) {
    console.error(`Snippet validation failed (${errors.length} problem${errors.length === 1 ? '' : 's'}):`);
    for (const error of errors) {
      console.error(`  - ${error}`);
    }
    process.exit(1);
  }
  const count = entries.reduce((n, e) => n + Object.keys(e.pack.snippets).length, 0);
  console.log(`Validated ${entries.length} snippet packs (${count} snippets).`);
  if (command === 'build') {
    const distFile = join(root, 'snippets', 'dist', 'index.json');
    const previous = existsSync(distFile) ? JSON.parse(readFileSync(distFile, 'utf8')) : undefined;
    const index = buildIndex(entries, previous);
    writeFileSync(distFile, JSON.stringify(index, null, 2) + '\n');
    console.log(`Wrote ${distFile}`);
    writeFileSync(join(root, 'docs', 'snippets.md'), renderDocs(index.packs));
    console.log('Wrote docs/snippets.md');
  }
}

if (process.argv[1] && fileURLToPath(import.meta.url) === process.argv[1]) {
  main();
}
