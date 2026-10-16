// Title: SolversLib + Pedro autonomous
// Description: An Autonomous that follows Pedro paths with SolversLib commands.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.api.PoseFactory;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import static com.pedropathing.api.Paths.line;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends CommandOpMode {
    private Follower follower;

    // Poses (headings in degrees)
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(${3:9, 111, 0});
    private final Pose endPose = p.of(${4:16, 128, 45});

    private Path path1;

    public void buildPaths() {
        path1 = line(startPose, endPose).linear(startPose, endPose);
    }

    @Override
    public void initialize() {
        super.reset();
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);
        buildPaths();

        schedule(new SequentialCommandGroup(
                new FollowPathCommand(follower, path1)
                $0
        ));
    }

    @Override
    public void run() {
        super.run();
        // The follower needs to be updated every loop.
        follower.update();
    }
}
