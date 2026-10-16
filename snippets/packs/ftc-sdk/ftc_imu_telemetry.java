// Title: IMU orientation telemetry
// Description: Show yaw, pitch, roll and turn rates.
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
AngularVelocity angularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);
telemetry.addData("Yaw (Z)", "%.2f Deg.", orientation.getYaw(AngleUnit.DEGREES));
telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
telemetry.addData("Roll (Y)", "%.2f Deg.", orientation.getRoll(AngleUnit.DEGREES));
telemetry.addData("Yaw rate", "%.2f Deg/Sec", angularVelocity.zRotationRate);
