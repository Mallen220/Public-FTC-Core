# Connecting to the robot

FTC Core talks to the Robot Controller over ADB, using the Platform-Tools it installs.

## Control Hub over Wi-Fi

Connect your computer to the Control Hub's Wi-Fi. FTC Core sees that you're on the robot's network and
connects to `192.168.43.1:5555` by itself, and the robot shows up in the status bar. **FTC:
Disconnect** stops it from reconnecting until you run **FTC: Connect to Robot**. To turn automatic
connection off, use `ftc.robot.autoConnect`.

## USB

A Control Hub on its USB-C port shows up right away. For a phone, turn on *USB debugging* in Developer
options, plug it in and accept the *Allow USB debugging?* prompt. Linux needs udev rules and Windows
usually needs the phone maker's driver; **FTC: Diagnose Connection** shows the steps for your system.

## Phones over Wi-Fi

Run **FTC: Configure Wireless ADB**. Phones in Wi-Fi Direct mode usually use `192.168.49.1`. Android 10
and older need a USB connection first. On Android 11 and newer, use *Wireless debugging › Pair device
with pairing code* and enter the code FTC Core asks for.

## Which robot gets the code

FTC Core tells robots apart by serial number, not by address. If one Robot Controller is connected it's
picked automatically; otherwise choose with **FTC: Select Robot** or from the status bar. Before every
deploy FTC Core checks that the device answering is the robot you picked. Driver Hubs can't run robot
code, so they're never offered.

## When it won't connect

Run **FTC: Diagnose Connection**. It checks ADB, your network, whether the robot answers and whether
the device has authorized this computer, and offers fixes.

If another program such as Android Studio already runs an ADB server, FTC Core uses it. To keep FTC
Core's ADB separate, set `ftc.adb.serverPort`.
