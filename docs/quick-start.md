# Quick start

From a fresh computer to code running on a Control Hub in about 15 minutes, most of it downloads.

## 1. Install

Install [VS Code](https://code.visualstudio.com/) 1.90 or newer, or [VSCodium](https://vscodium.com/), or another IDE of your choice that uses Open VSX
then the **FTC Code** extension. You don't need Android
Studio, Java or the Android SDK.

- [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=Mallen220.ftc-core) for VS Code
- or [Open VSX](https://open-vsx.org/extension/Mallen220/ftc-core) for VSCodium and other editors that use Open VSX.

## 2. Open a project

Open the folder that contains `TeamCode` and `FtcRobotController`, or run **FTC: Create New Project**
(<kbd>Ctrl/Cmd</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd>) to start from an official FTC SDK release. Choose
**Trust** when VS Code asks; in an untrusted folder FTC Core only shows information.

## 3. Set up the tools

Click **Set Up Environment** in the FTC view's *Getting Started* list. FTC Core installs the JDK and
Android packages that are missing, after you see the list and accept the licenses. See
[toolchain.md](toolchain.md).

## 4. Sync

FTC Core then syncs the project with Gradle and asks to add a few Java settings to
`.vscode/settings.json`. Say yes. The first sync takes a few minutes. After that, completion, hover
docs and go to definition work in `TeamCode`.

## 5. Connect

Connect your computer to the Control Hub's Wi-Fi and FTC Core connects by itself. USB works too. If it
doesn't connect, run **FTC: Diagnose Connection**. See [robot-connection.md](robot-connection.md).

## 6. Run on Robot

Click the rocket at the top right of the editor. FTC Core builds the app, installs it, checks that it
arrived and starts it. Compile errors go to the Problems panel. The **⌄** next to the rocket has Hot
Reload, Full Install, Build, Clean Build and the robot and log commands.

With [Sloth](sloth.md), FTC Core hot reloads your TeamCode in seconds when it can.

## Before a competition

Run **FTC: Prepare for Offline Use** while you have internet. See [offline.md](offline.md).
