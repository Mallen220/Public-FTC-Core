# Using an existing project

FTC Core works with any project based on the official
[FtcRobotController](https://github.com/FIRST-Tech-Challenge/FtcRobotController) repository. You can
keep using Android Studio on the same project.

FTC Core starts when the folder you open, or one directly inside it, has `TeamCode/build.gradle` or
`FtcRobotController/build.gradle`. If it finds more than one project, switch with **FTC: Select Project**.

FTC SDK 9.2 through 12.0 are known releases.

## Files FTC Core writes

- `local.properties`, so Gradle can find the Android SDK. It asks before changing an existing `sdk.dir`.
- `.vscode/settings.json`, on the first sync and only after asking.
- `build/ftc-vscode/`, the generated project model. It's safe to delete.
- Gradle files, but only from **Manage Libraries** or **Update FTC SDK**, and only after showing you the
  diff.

## With Android Studio

Both use the project's Gradle wrapper and build the same app. If Android Studio is building, FTC Core
waits.

An app installed from another computer is signed with a different key, so installing over it fails. See
[signature conflicts](troubleshooting.md#signature-conflict).

## Remote windows

FTC Core has to run on the computer connected to the robot, so it won't deploy from Remote SSH, WSL,
container or Codespaces windows.
