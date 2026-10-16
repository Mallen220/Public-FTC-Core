// Title: TeleOp (LinearOpMode)
// Description: A TeleOp OpMode that runs in one runOpMode method.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Initialize hardware here.
        $3
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            $0
            telemetry.update();
        }
    }
}
