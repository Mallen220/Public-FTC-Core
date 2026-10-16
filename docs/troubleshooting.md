# Troubleshooting

Most errors in FTC Core come with a button for the fix. This page covers the ones that need more
explanation. **FTC: Show Build Output** has the full build log, and **FTC: Show Extension Log** has FTC
Core's own.

## Setup

**FTC Core only shows information.** The folder isn't trusted. Run *Workspaces: Manage Workspace
Trust*.

**A package, license or `local.properties` is missing.** Run **FTC: Repair Toolchain**.

**Gradle runs on the wrong JDK.** **FTC: Check Toolchain** shows which JDK is used and why. If
`ftc.toolchain.jdkPath` is set, it always wins.

**Downloads fail.** School networks often block them. Try another network, or install the JDK and
Android SDK yourself and point `ftc.toolchain.jdkPath` and `ftc.toolchain.androidSdkPath` at them.

**_Bad CPU type in executable_ on a Mac.** Run `softwareupdate --install-rosetta`.

## Building

**Not available offline.** Something was never downloaded. Get online and run **FTC: Prepare for
Offline Use**.

**Another Gradle process is using this project.** Android Studio or a terminal is building it. Wait or
close it.

**Out of memory.** Close other programs and turn on `ftc.build.lowResourceMode`.

**A build file is locked (Windows).** Antivirus or OneDrive is holding a file in `build/`. Wait a
moment and run **FTC: Clean Build**.

## Deploying

FTC Core never uninstalls anything by itself. If a deploy fails, the robot keeps the app it had.

### Signature conflict

*The Robot Controller app on the robot was signed with a different key*
(`INSTALL_FAILED_UPDATE_INCOMPATIBLE`) means the app on the robot came from another computer or from
the REV Hardware Client. Android only lets the same key replace an app.

Uninstall the Robot Controller app (in the REV Hardware Client, or with
`adb uninstall com.qualcomm.ftcrobotcontroller`) and deploy again. Your configurations in
`/sdcard/FIRST` are kept. To avoid it, deploy from one computer or share
`~/.android/debug.keystore` across the team's computers.

### Hot reload failed on the robot

The Driver Station shows *User code threw an uncaught exception* right after a hot reload. Sloth
uploaded the code but loading it threw an error, and **FTC: Show Build Output** has the stack trace.

- If it's a known library bug, FTC Core names it. Run **FTC: Fix Known Library Problem**, then do a
  **Full Install**.
- If your code throws while loading, for example in a static initializer, fix it and hot reload again.
- If the error comes from a library and none of your code is in the stack trace, FTC Core names the
  library. **Report Library Crash** opens an earlier report of the same crash on GitHub, or a new one
  filled in for you to check and submit. Reported crashes become known library problems.
- Otherwise do a **Full Install**.

### Other deploy errors

- *Newer Robot Controller version on the robot*: update the project with **FTC: Update FTC SDK**, or
  uninstall the app on the robot.
- *Not enough storage*: delete old logs or videos on the robot.
- *Different device at the robot's address*: you swapped hubs. Pick the new one with **FTC: Select
  Robot**.

## Connecting

Run **FTC: Diagnose Connection** first. If it can't reach `192.168.43.1:5555`, check that you're on the
robot's Wi-Fi and the robot is on. For USB problems on Linux or Windows it shows the driver or udev
steps.

## IntelliSense

If imports are red or completion is empty, check that the **Project** view says *Java support: Ready*.
If not, run **FTC: Sync Project** and accept the `.vscode/settings.json` change. If that doesn't help,
run *Java: Clean Java Language Server Workspace*.

FTC SDK 12.0 compiles as Java 8, so newer syntax such as `var` is flagged on purpose.

## Reporting a problem

Open an issue at <https://github.com/Mallen220/Public-FTC-Core/issues> with the output of **FTC: Check
Toolchain** or **FTC: Diagnose Connection**, the end of the build output, and your OS, editor and FTC
SDK version. Remove anything you don't want public, like Wi-Fi passwords.
