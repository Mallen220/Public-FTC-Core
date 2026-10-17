# Connecting to the robot

FTC Core talks to the Robot Controller over ADB, using the Platform-Tools it installs.

## Control Hub over Wi-Fi

Join the Control Hub's Wi-Fi and FTC Core connects to `192.168.43.1:5555` by itself. The robot shows up
in the status bar. **FTC: Disconnect** stops automatic reconnects until you run **FTC: Connect to Robot**,
and `ftc.robot.autoConnect` turns them off.

## USB

A Control Hub on its USB-C port shows up right away. For a phone, turn on *USB debugging* in Developer
options, plug it in and accept the prompt. Linux needs udev rules and Windows usually needs the phone
maker's driver. **FTC: Diagnose Connection** shows the steps.

## Phones over Wi-Fi

Run **FTC: Configure Wireless ADB**. Phones in Wi-Fi Direct mode usually use `192.168.49.1`. Android 10
and older need USB first. On Android 11 and newer, use *Wireless debugging › Pair device with pairing
code* and enter the code FTC Core asks for.

## Which robot gets the code

FTC Core tells robots apart by serial number. With one Robot Controller connected it's picked
automatically. Otherwise choose with **FTC: Select Robot** or from the status bar. Driver Hubs can't run
robot code, so they're never offered.

## When it won't connect

Run **FTC: Diagnose Connection**. It checks ADB, your network and whether the robot answers, and offers
fixes.

If another program such as Android Studio already runs an ADB server, FTC Core uses it. To keep them
separate, set `ftc.adb.serverPort`.
