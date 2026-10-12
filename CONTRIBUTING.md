# Contributing

Most contributions here are library catalog updates: a new library, a new version, or a fix to what the
catalog says about one.

## Suggesting a change

Open an issue with the library, the version, and a link to where it's published or documented. That's
enough for us to update the catalog.

## Sending a pull request

Each library is one file in `catalog/manifests`, named after its `id`. To add a version, copy an
existing entry in `versions` and change it. To add a library, copy a similar manifest. Field
descriptions are in [the schema](catalog/schema/library-manifest.schema.json).

Then run:

```sh
npm install
npm run build
```

`build` checks every manifest and regenerates `catalog/dist/index.json`. Commit both. The check on your
pull request fails if `index.json` wasn't rebuilt.

A few rules:

- Every coordinate and version must exist in the listed Maven repository.
- Put the links you checked in `verification.sources` and today's date in `verification.checkedAt`.
- Use `"status": "documented"` for versions you haven't built in a real project.
- Don't rename a field or an `id`. Older versions of the extension still read this catalog.

## Contribution terms

This repository is under the [PolyForm Strict License 1.0.0](LICENSE.md), which doesn't allow changes.
These terms add what you need to contribute:

1. You may change the files to prepare a contribution to this repository, in a fork or a local copy.
   You may not distribute your changes in any other way.
2. When you submit a contribution (a pull request, a patch or an issue with content for the catalog),
   you give the copyright holder a perpetual, worldwide, royalty-free, irrevocable license to use,
   modify, distribute and sublicense it under any terms, and you confirm that you have the right to
   grant that license. You keep the copyright in your contribution.
