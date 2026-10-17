# Quick start

From a fresh computer to code running on a Control Hub in about 15 minutes, most of it downloads.

## 1. Install

Install [VS Code](https://code.visualstudio.com/) 1.90 or newer (or [VSCodium](https://vscodium.com/)),
then the FTC Core extension from the
[Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=Mallen220.ftc-core) or
[Open VSX](https://open-vsx.org/extension/Mallen220/ftc-core). You don't need Android Studio, Java or the
Android SDK.

## 2. Open a project

Open the folder that contains `TeamCode` and `FtcRobotController`, or run **FTC: Create New Project**
(<kbd>Ctrl/Cmd</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd>) to start from an official FTC SDK release. Choose
**Trust** when VS Code asks. In an untrusted folder FTC Core only shows information.

## 3. Set up the tools

Click **Set Up Environment** in the FTC view's *Getting Started* list. FTC Core lists what's missing and
installs it once you accept the licenses. See [toolchain.md](toolchain.md).

## 4. Sync

FTC Core syncs the project with Gradle and asks to add a few Java settings to `.vscode/settings.json`.
Say yes. The first sync takes a few minutes.

## 5. Connect

Join the Control Hub's Wi-Fi and FTC Core connects by itself. USB works too. If it doesn't connect, run
**FTC: Diagnose Connection**. See [robot-connection.md](robot-connection.md).

## 6. Run on Robot

Click the rocket at the top right of the editor. FTC Core builds the app, installs it and starts it.
Compile errors go to the Problems panel. With [Sloth](sloth.md), it hot reloads your TeamCode in seconds
when it can.

## Before a competition

Run **FTC: Prepare for Offline Use** while you have internet. See [offline.md](offline.md).
