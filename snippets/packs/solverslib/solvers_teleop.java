// Title: SolversLib TeleOp (CommandOpMode)
// Description: A TeleOp that runs the command scheduler.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends CommandOpMode {
    private GamepadEx driver;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        $0
    }

    @Override
    public void run() {
        super.run(); // runs the command scheduler
        telemetry.update();
    }
}
