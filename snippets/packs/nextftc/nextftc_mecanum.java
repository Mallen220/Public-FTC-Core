// Title: NextFTC mecanum drive
// Description: Drive a mecanum robot from the left stick and right stick x.
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.core.commands.Command;

Command driverControlled = new MecanumDriverControlled(
        ${1:frontLeft},
        ${2:frontRight},
        ${3:backLeft},
        ${4:backRight},
        Gamepads.gamepad1().leftStickY().negate(),
        Gamepads.gamepad1().leftStickX(),
        Gamepads.gamepad1().rightStickX());
driverControlled.schedule();
