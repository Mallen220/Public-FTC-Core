// Title: Ivy autonomous
// Description: An Autonomous OpMode that runs a sequence of Ivy commands: follow a path, wait, act, follow another path.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.api.PoseFactory;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private Follower follower;

    // Poses (headings in degrees)
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(${3:9, 111, 0});
    private final Pose scorePose = p.of(${4:16, 128, 45});
    private final Pose parkPose = p.of(${5:40, 120, 90});

    private Path scorePath;
    private Path parkPath;

    public void buildPaths() {
        scorePath = line(startPose, scorePose).linear(startPose, scorePose);
        parkPath = line(scorePose, parkPose).linear(scorePose, parkPose);
    }

    @Override
    public void init() {
        // The scheduler is static, so clear anything left over from an earlier run.
        Scheduler.reset();
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);
        buildPaths();
    }

    @Override
    public void start() {
        Scheduler.schedule(
                sequential(
                        follow(follower, scorePath),
                        waitMs(${6:500}),
                        instant(() -> {
                            $0
                        }),
                        follow(follower, parkPath)
                )
        );
    }

    @Override
    public void loop() {
        // Both must run every loop.
        follower.update();
        Scheduler.execute();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}
