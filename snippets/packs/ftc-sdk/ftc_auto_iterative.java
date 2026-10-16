// Title: Autonomous (iterative OpMode)
// Description: An Autonomous OpMode with init, start, loop and stop methods.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        $3
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        $0
    }
}
