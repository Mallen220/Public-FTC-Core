# Offline use

At competitions your laptop is on the robot's Wi-Fi with no internet, so prepare each project before you
go.

Run **FTC: Prepare for Offline Use** while you have internet. It downloads everything a build needs, then
runs an offline build to make sure nothing is missing. Run it again after adding a library or updating
the SDK.

`ftc.build.networkMode` picks how Gradle builds:

- `auto` (the default) builds offline when the internet isn't reachable. Being on only the robot's Wi-Fi
  counts as offline.
- `online` always lets Gradle use the network.
- `offline` always builds with `--offline`.

If an offline build needs something that was never downloaded, the error names it. Dependencies live in
Gradle's cache (`~/.gradle`), shared with Android Studio.
