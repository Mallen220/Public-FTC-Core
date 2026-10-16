// Title: Road Runner TeleOp drive
// Description: Drive from the gamepad and update the pose estimate.
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

drive.setDrivePowers(new PoseVelocity2d(
        new Vector2d(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x),
        -gamepad1.right_stick_x));
drive.updatePoseEstimate();
