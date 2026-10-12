# Sloth hot reload

[Sloth](https://github.com/Dairy-Foundation/Sloth) by Dairy Foundation loads changed TeamCode classes
into the running Robot Controller app. A hot reload takes seconds; a full install takes about a minute.

## Adding Sloth

Answer *Yes* to Sloth in **FTC: Create New Project**, or add it with **FTC: Manage Libraries**. Then do
one **Full Install**, because the app on the robot needs the Sloth runtime before hot reloads work.

FTC Dashboard needs its Sloth build, and FTC Core picks it for you. Panels works with Sloth as is.

## When FTC Core hot reloads

With `ftc.deploy.mode` on `auto`, **Run on Robot** hot reloads only when it's safe:

- this robot got a full install from this project on this computer, and nobody replaced the app since;
- no Gradle file, dependency, manifest, resource or asset changed since then;
- every changed file is in `org.firstinspires.ftc.teamcode`, and none is `@Pinned`.

Otherwise it does a full install and tells you why. **FTC: Hot Reload (Sloth)** forces a hot reload,
asking first if FTC Core would have done a full install.

After the upload FTC Core reads the robot log and tells you whether the robot loaded the new code,
will load it when the current OpMode stops, or failed to load it. For failures, see
[Troubleshooting](troubleshooting.md#hot-reload-failed-on-the-robot).

## Remove Sloth Remote

Hot-reloaded code lives on the robot in `/sdcard/FIRST/dairy/sloth`. **FTC: Remove Sloth Remote**
deletes it, so the robot goes back to its last full install. Use it when the robot seems to run old
code, or before someone else deploys from another computer.
