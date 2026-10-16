// Title: Road Runner autonomous
// Description: An Autonomous that builds a trajectory action and runs it.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(${3:0}, ${4:0}, Math.toRadians(${5:0}));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action trajectory = drive.actionBuilder(initialPose)
                $0
                .build();

        waitForStart();
        if (isStopRequested()) {
            return;
        }

        Actions.runBlocking(trajectory);
    }
}
