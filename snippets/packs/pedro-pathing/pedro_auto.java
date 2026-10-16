// Title: Pedro autonomous
// Description: An Autonomous OpMode that follows a chain of paths with a state machine.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.api.PoseFactory;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import static com.pedropathing.api.Paths.line;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private Follower follower;
    private int pathState;

    // Poses (headings in degrees)
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(${3:9, 111, 0});
    private final Pose endPose = p.of(${4:16, 128, 45});

    private Path path1;

    public void buildPaths() {
        path1 = line(startPose, endPose).linear(startPose, endPose);
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.follow(path1);
                pathState = 1;
                break;
            case 1:
                if (!follower.isBusy()) {
                    // The path is finished.
                    $0
                    pathState = -1;
                }
                break;
        }
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);
        buildPaths();
    }

    @Override
    public void start() {
        pathState = 0;
    }

    @Override
    public void loop() {
        // The follower must be updated every loop.
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("Path state", pathState);
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}
