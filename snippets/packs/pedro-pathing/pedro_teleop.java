// Title: Pedro TeleOp
// Description: A TeleOp OpMode that drives with the Pedro follower, field-centric.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.follower.ManualDrive;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private Follower follower;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(Pose.zero());
    }

    @Override
    public void loop() {
        // Pedro uses +x forwards, +y left and counterclockwise positive, while the gamepad sticks read
        // positive to the right and down, so all three axes are negated.
        follower.manual(ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()));
        follower.update();
        $0
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}
