# Toolchain

A build needs a JDK and an Android SDK. FTC Core uses what you already have and installs only what's
missing. For FTC SDK 12.0 that's JDK 17 to 21, Android Platform 30, the project's Build-Tools and
Platform-Tools. Older SDK releases need other versions, and FTC Core reads them from the project.

## Commands

- **FTC: Set Up Environment** installs what's missing.
- **FTC: Check Toolchain** shows what's used and where it came from.
- **FTC: Repair Toolchain** reinstalls anything damaged.
- **FTC: Manage Toolchains** removes JDKs and packages no open project needs.

Before anything downloads you see the list and the sizes, and you accept each Android license
yourself. Downloads are checksum-verified and resume if interrupted.

## Where things go

Everything goes in `~/.ftc-vscode` (on Windows, `C:\ftc-vscode` if your user folder has spaces).
Change it with `ftc.toolchain.directory`. FTC Core doesn't change `PATH`, `JAVA_HOME` or
`ANDROID_HOME`; it only passes the right JDK and SDK to the processes it starts.

To use your own JDK or SDK, set `ftc.toolchain.jdkPath` or `ftc.toolchain.androidSdkPath`.

## Android packages

FTC Core installs Android packages itself from Google's repository, in the same layout `sdkmanager`
uses, so Android Studio recognizes them. To use Google's `sdkmanager` instead, set
`ftc.toolchain.packageInstaller` to `sdkmanager`.

## The NDK

FTC projects declare an NDK, but a normal project doesn't need it. Without it the build prints
*Unable to strip the following libraries* and works fine. It's about 1 GB, so FTC Core installs it
only if you ask (`ftc.toolchain.installDeclaredNdk`).

## Slow computers

`ftc.build.lowResourceMode` runs Gradle with fewer workers and stops its daemon sooner.
