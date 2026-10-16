// Title: SolversLib + Pedro TeleOp
// Description: A TeleOp that drives the Pedro follower, field-centric.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends CommandOpMode {
    private Follower follower;

    @Override
    public void initialize() {
        follower = Constants.createFollower(hardwareMap);
        super.reset();
    }

    @Override
    public void run() {
        super.run();
        // Pedro uses +x forwards, +y left and counterclockwise positive, while the gamepad sticks read
        // positive to the right and down, so all three axes are negated.
        follower.manual(ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()));
        follower.update();
        $0
    }
}
