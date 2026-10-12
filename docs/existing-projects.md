# Using an existing project

FTC Core works with any project based on the official
[FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController) repository. You can
keep using Android Studio on the same project.

FTC Core starts when the folder you open, or a folder directly inside it, has `TeamCode/build.gradle`
or `FtcRobotController/build.gradle`. The **Project** view shows the FTC SDK version, Gradle versions,
libraries and whether Sloth is set up. If more than one project is found, switch with **FTC: Select
Project**.

FTC SDK 9.2 through 12.0 are known releases.

## Files FTC Core writes

- `local.properties`, so Gradle can find the Android SDK. FTC Core asks before changing an existing
  `sdk.dir`.
- `.vscode/settings.json`, on the first sync and only after asking, so the Java extension uses FTC
  Core's project model.
- `build/ftc-vscode/`, the generated project model. It's safe to delete.
- Gradle files, but only from **Manage Libraries** or **Update FTC SDK**, and only after showing you
  the diff.

## With Android Studio

Both use the project's Gradle wrapper and build the same app. Gradle runs one build per project at a
time, so if Android Studio is building, FTC Core waits.

An app installed from another computer is signed with a different key, so installing over it fails.
See [signature conflicts](troubleshooting.md#signature-conflict).

## Remote windows

FTC Core has to run on the computer connected to the robot. In Remote SSH, WSL, container or
Codespaces windows it won't deploy.
