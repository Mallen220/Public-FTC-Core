// Title: Mecanum drive (field-centric)
// Description: Field-centric mecanum drive using the IMU heading.
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

double y = -gamepad1.left_stick_y;
double x = gamepad1.left_stick_x;
double rx = gamepad1.right_stick_x;

if (gamepad1.options) {
    imu.resetYaw();
}

double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
rotX = rotX * 1.1; // counteract imperfect strafing

double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
${1:leftFront}.setPower((rotY + rotX + rx) / denominator);
${2:leftBack}.setPower((rotY - rotX + rx) / denominator);
${3:rightFront}.setPower((rotY - rotX - rx) / denominator);
${4:rightBack}.setPower((rotY + rotX - rx) / denominator);
