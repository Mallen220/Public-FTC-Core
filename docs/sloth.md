# Sloth hot reload

[Sloth](https://github.com/Dairy-Foundation/Sloth) by Dairy Foundation loads changed TeamCode classes
into the running Robot Controller app. A hot reload takes seconds. A full install takes about a minute.

## Adding Sloth

Answer *Yes* to Sloth in **FTC: Create New Project**, or add it with **FTC: Manage Libraries**. Then do
one **Full Install** so the robot has the Sloth runtime.

FTC Dashboard needs its Sloth build, which FTC Core picks for you. Panels works with Sloth as is.

## When FTC Core hot reloads

With `ftc.deploy.mode` on `auto`, **Run on Robot** hot reloads only when it's safe:

- this robot got a full install from this project on this computer, and nobody replaced the app since;
- no Gradle file, dependency, manifest, resource or asset changed since then;
- every changed file is in `org.firstinspires.ftc.teamcode`, and none is `@Pinned`.

Otherwise it does a full install and tells you why. **FTC: Hot Reload (Sloth)** forces a hot reload,
asking first if a full install was needed.

Afterwards FTC Core reads the robot log and tells you whether the new code loaded, will load when the
current OpMode stops, or failed. For failures, see
[Troubleshooting](troubleshooting.md#hot-reload-failed-on-the-robot).

## Remove Sloth Remote

Hot-reloaded code lives on the robot in `/sdcard/FIRST/dairy/sloth`. **FTC: Remove Sloth Remote** deletes
it and returns the robot to its last full install. Use it when the robot seems to run old code, or before
someone else deploys from another computer.
