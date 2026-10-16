// Title: NextFTC Autonomous
// Description: An Autonomous OpMode that schedules a sequence when Start is pressed.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends NextFTCOpMode {
    public ${TM_FILENAME_BASE}() {
        addComponents(
                new SubsystemComponent(${3}),
                BulkReadComponent.INSTANCE
        );
    }

    private Command autonomous() {
        return new SequentialGroup(
                $0
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomous().schedule();
    }
}
