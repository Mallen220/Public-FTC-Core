// Title: Autonomous (LinearOpMode)
// Description: An Autonomous OpMode that runs in one runOpMode method.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Initialize hardware here.
        $3
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) {
            return;
        }

        $0
    }
}
