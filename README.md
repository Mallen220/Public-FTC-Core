# FTC Core

FTC Core is a VS Code extension for building and deploying FIRST Tech Challenge robot code without
Android Studio. Install it from the
[Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=Mallen220.ftc-core) for
VS Code, or from [Open VSX](https://open-vsx.org/extension/Mallen220/ftc-core) for VSCodium and other
editors that use it.

This repository holds the extension's documentation, library catalog and code snippets. The extension's
source code isn't public.

> FTC Core is an unofficial community project. It isn't made, endorsed or supported by *FIRST*® or the
> *FIRST*® Tech Challenge. *FIRST*®, *FIRST*® Tech Challenge and FTC® are trademarks of *FIRST*.

## Documentation

- [Quick start](docs/quick-start.md)
- [Using an existing project](docs/existing-projects.md)
- [Toolchain](docs/toolchain.md)
- [Offline use](docs/offline.md)
- [Connecting to the robot](docs/robot-connection.md)
- [Sloth hot reload](docs/sloth.md)
- [Libraries](docs/libraries.md)
- [Snippets](docs/snippets.md)
- [Updating the FTC SDK](docs/sdk-updates.md)
- [Troubleshooting](docs/troubleshooting.md)

## Library catalog and snippets

**FTC: Manage Libraries** reads its list of libraries and versions from [`catalog/`](catalog), and the
code snippets come from [`snippets/`](snippets). The extension downloads both once a day, so a merged
change reaches everyone without an extension update.

If a library or snippet is missing or out of date, [open an issue](https://github.com/Mallen220/Public-FTC-Core/issues)
or send a pull request. See [CONTRIBUTING.md](CONTRIBUTING.md).

## Bugs

Report problems in [issues](https://github.com/Mallen220/Public-FTC-Core/issues) and include the output
of **FTC: Check Toolchain** or **FTC: Diagnose Connection**.

## License

[PolyForm Strict 1.0.0](LICENSE.md).
