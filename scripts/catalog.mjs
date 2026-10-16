// Checks the library manifests and builds catalog/dist/index.json.
//   node scripts/catalog.mjs validate
//   node scripts/catalog.mjs build
import Ajv from 'ajv';
import { existsSync, readdirSync, readFileSync, writeFileSync } from 'fs';
import { basename, dirname, join } from 'path';
import { fileURLToPath } from 'url';

const root = join(dirname(fileURLToPath(import.meta.url)), '..');
const schema = JSON.parse(readFileSync(join(root, 'catalog', 'schema', 'library-manifest.schema.json'), 'utf8'));
const manifestDir = join(root, 'catalog', 'manifests');

export function loadManifests(dir = manifestDir) {
  return readdirSync(dir)
    .filter((f) => f.endsWith('.json'))
    .sort()
    .map((file) => ({ file, manifest: JSON.parse(readFileSync(join(dir, file), 'utf8')) }));
}

export function validateManifests(entries) {
  const ajv = new Ajv({ allErrors: true, strict: true, allowUnionTypes: true });
  const validate = ajv.compile(schema);
  const errors = [];
  const ids = new Set(entries.map((e) => e.manifest.id));
  const seen = new Set();
  for (const { file, manifest } of entries) {
    const where = `${file}`;
    if (!validate(manifest)) {
      for (const error of validate.errors ?? []) {
        errors.push(`${where}: ${error.instancePath || '/'} ${error.message}`);
      }
      continue;
    }
    if (basename(file, '.json') !== manifest.id) {
      errors.push(`${where}: file name must be "${manifest.id}.json"`);
    }
    if (seen.has(manifest.id)) {
      errors.push(`${where}: duplicate id ${manifest.id}`);
    }
    seen.add(manifest.id);
    const declared = new Set((manifest.artifacts ?? []).map((a) => a.coordinate));
    if (!manifest.builtIn && declared.size === 0) {
      errors.push(`${where}: a library that is not built into the SDK must declare artifacts`);
    }
    if (!manifest.builtIn && !manifest.install) {
      errors.push(`${where}: install.target is required for libraries that add dependencies`);
    }
    for (const version of manifest.versions) {
      for (const coordinate of Object.keys(version.artifacts)) {
        if (!declared.has(coordinate)) {
          errors.push(`${where}: version ${version.version} lists ${coordinate}, which is not in artifacts`);
        }
      }
      for (const artifact of manifest.artifacts ?? []) {
        if (artifact.role !== 'optional' && !manifest.builtIn && !(artifact.coordinate in version.artifacts)) {
          errors.push(
            `${where}: version ${version.version} has no version for required artifact ${artifact.coordinate}`,
          );
        }
      }
    }
    const references = [
      manifest.sloth?.variant,
      ...(manifest.requires ?? []).flatMap((r) => [r.library, r.whenSloth]),
    ].filter(Boolean);
    for (const reference of references) {
      if (!ids.has(reference)) {
        errors.push(`${where}: references unknown library "${reference}"`);
      }
    }
    if (manifest.sloth?.compatibility === 'variant' && !manifest.sloth.variant) {
      errors.push(`${where}: sloth.compatibility "variant" needs sloth.variant`);
    }
    for (const plugin of manifest.install?.gradlePlugins ?? []) {
      if (!declared.has(plugin.classpathCoordinate)) {
        errors.push(`${where}: plugin classpath ${plugin.classpathCoordinate} must be listed in artifacts`);
      }
    }
    for (const exclusion of manifest.install?.exclusions ?? []) {
      if (!declared.has(exclusion.coordinate)) {
        errors.push(`${where}: exclusion target ${exclusion.coordinate} must be listed in artifacts`);
      }
    }
    const issueIds = new Set();
    for (const issue of manifest.knownIssues ?? []) {
      if (issueIds.has(issue.id)) {
        errors.push(`${where}: duplicate known issue id ${issue.id}`);
      }
      issueIds.add(issue.id);
      if (!declared.has(issue.coordinate)) {
        errors.push(`${where}: known issue ${issue.id} names ${issue.coordinate}, which is not in artifacts`);
      }
      if (issue.fixVersion && issue.versions.includes(issue.fixVersion)) {
        errors.push(`${where}: known issue ${issue.id} offers ${issue.fixVersion}, which it lists as affected`);
      }
      // The catalog itself must never recommend a version with a crash-level defect.
      for (const version of manifest.versions) {
        if (issue.severity === 'error' && issue.versions.includes(version.artifacts[issue.coordinate])) {
          errors.push(
            `${where}: version ${version.version} installs ${issue.coordinate} ${version.artifacts[issue.coordinate]}, which has known issue ${issue.id}`,
          );
        }
      }
    }
  }
  return errors;
}

export function buildIndex(entries, previous) {
  const libraries = entries.map((e) => {
    const { $schema, ...rest } = e.manifest;
    void $schema;
    return rest;
  });
  // Keep the old date when nothing changed. The extension ignores a downloaded catalog that looks older
  // than the copy it ships with.
  const unchanged = previous && JSON.stringify(previous.libraries) === JSON.stringify(libraries);
  return {
    schemaVersion: 1,
    generated: unchanged ? previous.generated : new Date().toISOString().slice(0, 10),
    libraries,
  };
}

function main() {
  const command = process.argv[2] ?? 'validate';
  const entries = loadManifests();
  const errors = validateManifests(entries);
  if (errors.length > 0) {
    console.error(`Catalog validation failed (${errors.length} problem${errors.length === 1 ? '' : 's'}):`);
    for (const error of errors) {
      console.error(`  - ${error}`);
    }
    process.exit(1);
  }
  console.log(`Validated ${entries.length} library manifests.`);
  if (command === 'build') {
    const distFile = join(root, 'catalog', 'dist', 'index.json');
    const previous = existsSync(distFile) ? JSON.parse(readFileSync(distFile, 'utf8')) : undefined;
    const index = JSON.stringify(buildIndex(entries, previous), null, 2) + '\n';
    writeFileSync(distFile, index);
    console.log(`Wrote ${distFile}`);
  }
}

if (process.argv[1] && fileURLToPath(import.meta.url) === process.argv[1]) {
  main();
}
