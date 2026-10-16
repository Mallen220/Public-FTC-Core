// Title: Turn to heading
// Description: Turn in place to a heading with simple proportional control.
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.util.Range;

double headingError = ${1:targetHeading} - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
while (opModeIsActive() && Math.abs(headingError) > ${2:1.0}) {
    headingError = ${1:targetHeading} - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    while (headingError > 180) headingError -= 360;
    while (headingError <= -180) headingError += 360;
    double turn = Range.clip(headingError * ${3:0.02}, -${4:0.5}, ${4:0.5});
    ${5:leftFront}.setPower(-turn);
    ${6:leftBack}.setPower(-turn);
    ${7:rightFront}.setPower(turn);
    ${8:rightBack}.setPower(turn);
}
$0
