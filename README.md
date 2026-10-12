# FTC Core

FTC Core is a VS Code and VSCodium extension for building and deploying FIRST Tech Challenge robot code
without Android Studio. Install it from the
[Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=Mallen220.ftc-core).

This repository has the extension's documentation and its library catalog. The extension's source code
isn't public.

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
- [Updating the FTC SDK](docs/sdk-updates.md)
- [Troubleshooting](docs/troubleshooting.md)

## Library catalog

**FTC: Manage Libraries** in the extension gets its list of libraries and versions from
[`catalog/`](catalog). The extension downloads the catalog from this repository once a day, so a change
merged here reaches everyone without an extension update.

If a library is missing or out of date, [open an issue](https://github.com/Mallen220/Public-FTC-Core/issues)
or send a pull request. [CONTRIBUTING.md](CONTRIBUTING.md) explains how.

## Bugs

Report problems with the extension in [issues](https://github.com/Mallen220/Public-FTC-Core/issues).
Include the output of **FTC: Check Toolchain** or **FTC: Diagnose Connection**.

## License

[PolyForm Strict 1.0.0](LICENSE.md).
