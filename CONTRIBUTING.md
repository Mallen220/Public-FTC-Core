# Contributing

Most contributions here are library catalog updates (a new library, a new version, or a fix to what the
catalog says about one) and code snippets.

## Suggesting a change

Open an issue with the library, the version, and a link to where it's published or documented. That's
enough for us to update the catalog.

## Snippets

Snippets are optional but encouraged. A pull request that adds a library or a version doesn't need any, and
you can send them in a later one. If you know the library well, start with the setup block or OpMode that people
retype most.

Each snippet pack is a folder in `snippets/packs`. The folder name is the pack's id. It holds one `pack.json`
and one `.java` file for each snippet:

```
snippets/packs/pedro-pathing/
  pack.json
  pedro_auto.java
  pedro_teleop.java
```

### Adding a snippet

Add a `.java` file to the pack's folder and name it after the prefix people will type. The name has to start
with the pack's prefix (`pedro_` here) and can contain lowercase letters, digits and underscores.

```java
// Title: Pedro TeleOp
// Description: A TeleOp OpMode that drives with the Pedro follower, field-centric.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private Follower follower;
    ...
}
```

  [VS Code snippet syntax](https://code.visualstudio.com/docs/editing/userdefinedsnippets#_snippet-syntax):
  `${1:name}` is a tab stop, `$0` is where the cursor ends up, and `${TM_FILENAME_BASE}` is the file's name,
  which is also the class name.

A file with a top-level class named `${TM_FILENAME_BASE}` is a whole-file snippet. FTC Core offers it only in a
file that has no class yet. Every other snippet is inserted at the cursor.

### Adding a pack

Create a folder named after the pack's id, with a `pack.json`:

```json
{
  "$schema": "../../schema/pack-metadata.schema.json",
  "name": "Pedro Pathing",
  "description": "Pedro Pathing 3: paths, poses, the follower and manual driving.",
  "appliesTo": "Pedro Pathing 3.0 or newer.",
  "prefix": "pedro_",
  "requires": { "anyOf": [{ "coordinate": "com.pedropathing:core", "min": "3.0.0" }] }
}
```

`requires` decides which projects get the pack, by dependency and FTC SDK version. A pack without it is offered
in every FTC project. Give a library's old and new APIs separate packs with different version ranges, as the
Pedro Pathing 3 pack does, so nobody gets snippets that don't compile. Field descriptions are in
[the schema](snippets/schema/pack-metadata.schema.json). Don't rename a pack's folder, because older versions of
the extension still read it as the pack's id.

### Checking your change

Run `npm run build` before you commit. It checks every pack (the schemas, prefixes, balanced brackets, imports),
rebuilds `snippets/dist/index.json` and regenerates `docs/snippets.md`. Errors name the file and the line. The
check on your pull request fails if the generated files weren't rebuilt. FTC Core downloads `snippets/dist/index.json`
once a day. Check a new snippet against the library's published classes, and say which version in the pull request.

## Sending a pull request

Each library is one file in `catalog/manifests`, named after its `id`. To add a library, copy a similar
manifest. Field descriptions are in [the schema](catalog/schema/library-manifest.schema.json).

New releases don't need an entry: FTC Core finds them in the library's Maven repository. Add one, by
copying an existing entry in `versions`, only when its artifacts go together differently from the
release before it or it needs notes.

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
- If the library's Java packages don't start with its Maven groups, list them in `packages`. FTC Core uses
  them to tell which library a robot crash came from.
- Don't rename a field or an `id`. Older versions of the extension still read this catalog and these snippets.

Snippets for the library are encouraged but not required. The [Snippets](#snippets) section explains how to add them.

## Contribution terms

This repository is under the [PolyForm Strict License 1.0.0](LICENSE.md), which doesn't allow changes.
These terms add what you need to contribute:

1. You may change the files to prepare a contribution to this repository, in a fork or a local copy.
   You may not distribute your changes in any other way.
2. When you submit a contribution (a pull request, a patch or an issue with content for the catalog),
   you give the copyright holder a perpetual, worldwide, royalty-free, irrevocable license to use,
   modify, distribute and sublicense it under any terms, and you confirm that you have the right to
   grant that license. You keep the copyright in your contribution.
