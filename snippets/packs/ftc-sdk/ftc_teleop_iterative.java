// Title: TeleOp (iterative OpMode)
// Description: A TeleOp OpMode with init, start, loop and stop methods.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        $3
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        $0
        telemetry.addData("Run time", runtime.toString());
    }

    @Override
    public void stop() {
    }
}
