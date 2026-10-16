// Looks for library releases on Maven that the catalog doesn't list yet. The scheduled "Library updates"
// workflow adds them to the manifests and opens a pull request, so nobody has to watch for releases.
//   node scripts/library-updates.mjs                     report only
//   node scripts/library-updates.mjs --write             also add the releases to catalog/manifests
//   node scripts/library-updates.mjs --summary <file>    write the pull request description to <file>
// In GitHub Actions it also sets the step outputs `count` and `title`.
import { appendFileSync, readFileSync, writeFileSync } from 'fs';
import { dirname, join } from 'path';
import { fileURLToPath } from 'url';
import { loadManifests, validateManifests } from './catalog.mjs';

const root = join(dirname(fileURLToPath(import.meta.url)), '..');
const manifestDir = join(root, 'catalog', 'manifests');
const MAVEN_CENTRAL = 'https://repo1.maven.org/maven2';
/** Same rule as the schema's artifact versions; anything else is never copied into a manifest. */
const SAFE_VERSION = /^[A-Za-z0-9][A-Za-z0-9._+-]*$/;
const MAX_LINE = 120;

function parseVersion(text) {
  const match = /(\d+(?:[._]\d+)*)/.exec(text);
  return match ? match[1].split(/[._]/).map((part) => Number.parseInt(part, 10)) : [];
}

function compareVersions(a, b) {
  const left = parseVersion(a);
  const right = parseVersion(b);
  for (let i = 0; i < Math.max(left.length, right.length); i++) {
    const diff = (left[i] ?? 0) - (right[i] ?? 0);
    if (diff !== 0) {
      return diff < 0 ? -1 : 1;
    }
  }
  return 0;
}

const newest = (versions) => [...versions].sort(compareVersions).at(-1);

/** Letters before any `+build` suffix mark a pre-release: 1.0.3-beta.1. 0.3.2+0.6.0 is a release. */
function isPrerelease(version) {
  return /[a-z]/i.test(version.split('+')[0]);
}

/** Versions, `release`, `latest` and `lastUpdated` (yyyy-mm-dd) of a maven-metadata.xml; undefined for anything else. */
function parseMetadata(xml) {
  if (!/<metadata[\s>]/.test(xml)) {
    return undefined;
  }
  const block = /<versions>([\s\S]*?)<\/versions>/.exec(xml)?.[1] ?? '';
  const tag = (name) => new RegExp(`<${name}>\\s*([^<\\s]+)\\s*</${name}>`).exec(xml)?.[1];
  const stamp = tag('lastUpdated');
  return {
    versions: [...block.matchAll(/<version>\s*([^<\s]+)\s*<\/version>/g)].map((m) => m[1]),
    release: tag('release'),
    latest: tag('latest'),
    lastUpdated: /^\d{8}/.test(stamp ?? '')
      ? `${stamp.slice(0, 4)}-${stamp.slice(4, 6)}-${stamp.slice(6, 8)}`
      : undefined,
  };
}

/**
 * Where an artifact's metadata is published: the manifest's own verification sources for it, otherwise the
 * library's repositories and Maven Central. The extension looks in the same places.
 */
function metadataUrls(manifest, coordinate) {
  const [group, name] = coordinate.split(':');
  const suffix = `/${group.replace(/\./g, '/')}/${name}/maven-metadata.xml`;
  const https = (url) => url.startsWith('https://');
  const listed = manifest.verification.sources.filter((s) => s.endsWith(suffix) && https(s));
  if (listed.length > 0) {
    return listed;
  }
  const repositories = [...(manifest.mavenRepositories ?? []).map((r) => r.url), MAVEN_CENTRAL];
  return [...new Set(repositories.filter(https).map((r) => `${r.replace(/\/+$/, '')}${suffix}`))];
}

/** Artifacts that every release in the manifest gives the same version, such as Sloth and its Load plugin. */
function releaseGroups(manifest) {
  const groups = new Map();
  for (const { coordinate } of manifest.artifacts ?? []) {
    const versions = manifest.versions.map((v) => v.artifacts[coordinate] ?? null);
    const key = versions.every((v) => v === null) ? `alone:${coordinate}` : JSON.stringify(versions);
    groups.set(key, [...(groups.get(key) ?? []), coordinate]);
  }
  return [...groups.values()];
}

function knownIssue(manifest, coordinate, version) {
  return (manifest.knownIssues ?? []).find(
    (i) => i.severity === 'error' && i.coordinate === coordinate && i.versions.includes(version),
  );
}

/**
 * The release to add for a library, built from the newest release in the manifest: its main artifact moves
 * to the newest stable version on Maven, artifacts released together with it follow, and the rest keep
 * their versions for a person to check. `published` maps group:name to parsed metadata (see readMetadata).
 * Returns undefined when there is nothing new, or `{ problem }` when it can't tell.
 */
function proposeRelease(manifest, published) {
  if (manifest.builtIn || !manifest.artifacts?.length) {
    return undefined;
  }
  const stable = manifest.versions.filter((v) => !isPrerelease(v.version));
  const latest = (stable.length > 0 ? stable : manifest.versions).reduce((a, b) =>
    compareVersions(b.version, a.version) > 0 ? b : a,
  );
  const primaries = manifest.artifacts.filter((a) => a.role === 'primary').map((a) => a.coordinate);
  // The artifact whose version is the library's version (Road Runner: core, not ftc).
  const key = primaries.find((c) => latest.artifacts[c] === latest.version) ?? primaries[0];
  const keyMeta = published.get(key);
  if (!keyMeta) {
    return { problem: `couldn't read the published versions of \`${key}\`` };
  }
  const inCatalog = newest(manifest.versions.map((v) => v.artifacts[key]).filter(Boolean));
  const target = newest(
    keyMeta.versions.filter(
      (v) =>
        SAFE_VERSION.test(v) &&
        !isPrerelease(v) &&
        !knownIssue(manifest, key, v) &&
        (!inCatalog || compareVersions(v, inCatalog) > 0),
    ),
  );
  if (!target) {
    return undefined;
  }
  if (manifest.versions.some((v) => v.version === target)) {
    return { problem: `\`${key}\` ${target} is on Maven, but the manifest already has a release called ${target}` };
  }
  const group = releaseGroups(manifest).find((g) => g.includes(key)) ?? [key];
  const label = (coordinate, version) => {
    const issue = knownIssue(manifest, coordinate, version);
    return issue
      ? `${version} (known issue \`${issue.id}\`)`
      : isPrerelease(version)
        ? `${version} (pre-release)`
        : version;
  };
  const date = keyMeta.sources.find((s) => s.release === target || s.latest === target)?.lastUpdated;
  const artifacts = {};
  const lines = [];
  for (const [coordinate, previous] of Object.entries(latest.artifacts)) {
    const meta = published.get(coordinate);
    if (coordinate === key) {
      artifacts[coordinate] = target;
      lines.push(`\`${coordinate}\` ${previous} → ${target}${date ? `, published ${date}` : ''}`);
    } else if (group.includes(coordinate)) {
      if (!meta) {
        return {
          problem: `couldn't read the published versions of \`${coordinate}\`, which comes out with \`${key}\``,
        };
      }
      if (meta.versions.includes(target) && !knownIssue(manifest, coordinate, target)) {
        artifacts[coordinate] = target;
        lines.push(`\`${coordinate}\` ${previous} → ${target}, released together with \`${key}\``);
      } else {
        artifacts[coordinate] = previous;
        lines.push(
          `\`${coordinate}\` kept at ${previous}: it usually comes out with \`${key}\`, but there is no ${target}`,
        );
      }
    } else {
      artifacts[coordinate] = previous;
      const newer = (meta?.versions ?? [])
        .filter((v) => SAFE_VERSION.test(v) && compareVersions(v, previous) > 0)
        .sort(compareVersions);
      lines.push(
        `\`${coordinate}\` kept at ${previous}${newer.length > 0 ? `. Also on Maven: ${newer.map((v) => label(coordinate, v)).join(', ')}` : meta ? '' : " (couldn't read its versions)"}`,
      );
    }
  }
  const entry = {
    version: target,
    ...(date ? { released: date } : {}),
    ...(latest.sdk ? { sdk: latest.sdk } : {}),
    artifacts,
  };
  return { entry, lines, previous: latest.version };
}

/** Versions of an artifact from every repository that has it, or undefined when none could be read. */
async function readMetadata(manifest, coordinate) {
  const sources = [];
  for (const url of metadataUrls(manifest, coordinate)) {
    try {
      const response = await fetch(url, { redirect: 'follow', signal: AbortSignal.timeout(15000) });
      const meta = response.ok ? parseMetadata(await response.text()) : undefined;
      if (meta) {
        sources.push({ url, ...meta });
      }
    } catch (error) {
      console.warn(`  ${url}: ${error.message}`);
    }
  }
  return sources.length > 0 ? { versions: [...new Set(sources.flatMap((s) => s.versions))], sources } : undefined;
}

/** One `"key": value` line of a release; objects stay on the line when it fits, as the manifests write them. */
function formatField(key, value, indent) {
  const prefix = `${indent}${JSON.stringify(key)}: `;
  if (!value || typeof value !== 'object') {
    return `${prefix}${JSON.stringify(value)}`;
  }
  const fields = Object.entries(value).map(([k, v]) => `${JSON.stringify(k)}: ${JSON.stringify(v)}`);
  const inline = `${prefix}{ ${fields.join(', ')} }`;
  return inline.length <= MAX_LINE
    ? inline
    : `${prefix}{\n${fields.map((f) => `${indent}  ${f}`).join(',\n')}\n${indent}}`;
}

/**
 * Adds a release at the top of a manifest's `versions`, in the file's own style so the diff is just the new
 * release. Falls back to rewriting the file when the style isn't recognized.
 */
function insertRelease(text, entry) {
  const match = /^[ \t]*"versions": \[\r?\n([ \t]*)\{\r?\n([ \t]*)"/m.exec(text);
  if (match) {
    const [, outer, inner] = match;
    const fields = Object.entries(entry).map(([k, v]) => formatField(k, v, inner));
    const at = text.indexOf('\n', match.index) + 1;
    const updated = `${text.slice(0, at)}${outer}{\n${fields.join(',\n')}\n${outer}},\n${text.slice(at)}`;
    try {
      if (JSON.stringify(JSON.parse(updated).versions[0]) === JSON.stringify(entry)) {
        return updated;
      }
    } catch {
      // Not valid JSON; rewrite the file instead.
    }
  }
  const manifest = JSON.parse(text);
  manifest.versions.unshift(entry);
  return `${JSON.stringify(manifest, null, 2)}\n`;
}

function describe(proposals, problems) {
  const sections = proposals.map(
    (p) =>
      `### ${p.manifest.name} ${p.entry.version}\n\nAfter ${p.previous}.\n\n${p.lines.map((l) => `- ${l}`).join('\n')}\n\nRelease notes: ${p.manifest.repository}${p.manifest.repository.startsWith('https://github.com/') ? '/releases' : ''}`,
  );
  return [
    "The scheduled library check found releases on Maven that the catalog doesn't list yet. Each is added as a",
    '`documented` release with the artifact pairing and FTC SDK range of the release before it. Nobody has',
    'checked them yet.',
    '',
    ...sections.flatMap((s) => [s, '']),
    ...(problems.length > 0 ? ["### Couldn't check", '', ...problems.map((p) => `- ${p}`), ''] : []),
    '### Before merging',
    '',
    "- [ ] Check each pairing against the library's install docs or quickstart, along with the FTC SDK range and Sloth compatibility.",
    '- [ ] Add upgrade notes or known issues the release needs.',
    '- [ ] Update the version table in `docs/libraries.md`.',
    '',
    'Merging publishes the releases to FTC Core users within a day. Closing this pull request without merging',
    'stops the same set of releases from being proposed again.',
    '',
  ].join('\n');
}

async function main() {
  const args = process.argv.slice(2);
  const write = args.includes('--write');
  const summary = args.includes('--summary') ? args[args.indexOf('--summary') + 1] : undefined;
  const proposals = [];
  const problems = [];
  for (const { file, manifest } of loadManifests()) {
    if (manifest.builtIn) {
      continue;
    }
    const published = new Map();
    for (const { coordinate } of manifest.artifacts ?? []) {
      const meta = await readMetadata(manifest, coordinate);
      if (meta) {
        published.set(coordinate, meta);
      }
    }
    const result = proposeRelease(manifest, published);
    if (result?.problem) {
      problems.push(`${manifest.name}: ${result.problem}`);
      console.warn(`${manifest.name}: ${result.problem.replace(/`/g, '')}`);
    } else if (result) {
      proposals.push({ file, manifest, ...result });
      console.log(`${manifest.name} ${result.entry.version} (catalog has ${result.previous})`);
      for (const line of result.lines) {
        console.log(`  ${line.replace(/`/g, '')}`);
      }
    } else {
      console.log(`${manifest.name}: up to date`);
    }
  }
  if (write) {
    for (const { file, entry } of proposals) {
      const path = join(manifestDir, file);
      writeFileSync(path, insertRelease(readFileSync(path, 'utf8'), entry));
    }
    const errors = validateManifests(loadManifests());
    if (errors.length > 0) {
      console.error(`The new releases don't pass the catalog checks:\n  ${errors.join('\n  ')}`);
      process.exit(1);
    }
  }
  const title =
    proposals.length > 0
      ? `Library updates: ${proposals.map((p) => `${p.manifest.name} ${p.entry.version}`).join(', ')}`
      : '';
  if (summary) {
    writeFileSync(summary, describe(proposals, problems));
  }
  if (process.env.GITHUB_OUTPUT) {
    appendFileSync(process.env.GITHUB_OUTPUT, `count=${proposals.length}\ntitle=${title}\n`);
  }
}

await main();
