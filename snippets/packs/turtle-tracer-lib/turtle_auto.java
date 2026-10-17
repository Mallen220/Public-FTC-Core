// Title: Turtle Tracer autonomous
// Description: An Autonomous OpMode that reads its poses from a .turt file, follows a path and runs an action part-way along it.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.turtletracerlib.TurtleTracerReader;
import com.turtletracerlib.pathing.ProgressTracker;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import java.io.IOException;
import static com.pedropathing.api.Paths.line;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private Follower follower;
    private ProgressTracker tracker;
    private int pathState;

    private Pose startPose;
    private Pose scorePose;
    private Path scorePath;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        // Poses come from src/main/assets/AutoPaths/${3:AutoRoutine.turt}, so editing the path in
        // Turtle Tracer changes the robot without regenerating code.
        TurtleTracerReader reader;
        try {
            reader = new TurtleTracerReader("${3:AutoRoutine.turt}", hardwareMap.appContext);
        } catch (IOException e) {
            throw new RuntimeException("Could not load ${3:AutoRoutine.turt} from assets/AutoPaths", e);
        }
        startPose = reader.get("startPoint");
        scorePose = reader.get("${4:Score}");

        follower.setPose(startPose);
        scorePath = line(startPose, scorePose).linear(startPose, scorePose);

        tracker = new ProgressTracker(follower, telemetry);
        tracker.setCurrentPath(scorePath);
        tracker.onParametric(${5:0.75}, () -> {
            $0
        });
    }

    @Override
    public void start() {
        pathState = 0;
    }

    @Override
    public void loop() {
        // Both must run every loop.
        follower.update();
        tracker.update();

        switch (pathState) {
            case 0:
                follower.follow(scorePath);
                pathState = 1;
                break;
            case 1:
                if (!follower.isBusy()) {
                    // The path is finished.
                    pathState = -1;
                }
                break;
        }

        telemetry.addData("Path state", pathState);
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}
