// Title: Arcade drive
// Description: Drive with one stick for speed and one for turning.
import com.qualcomm.robotcore.util.Range;

double drive = -gamepad1.left_stick_y;
double turn = gamepad1.right_stick_x;
${1:leftDrive}.setPower(Range.clip(drive + turn, -1.0, 1.0));
${2:rightDrive}.setPower(Range.clip(drive - turn, -1.0, 1.0));
