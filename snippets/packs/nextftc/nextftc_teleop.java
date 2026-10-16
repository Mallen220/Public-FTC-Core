// Title: NextFTC TeleOp
// Description: A TeleOp OpMode with the components most teams use.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.core.components.BindingsComponent;

@TeleOp(name = "${1:${TM_FILENAME_BASE}}", group = "${2:TeleOp}")
public class ${TM_FILENAME_BASE} extends NextFTCOpMode {
    public ${TM_FILENAME_BASE}() {
        addComponents(
                new SubsystemComponent(${3}),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        $0
    }

    @Override
    public void onStartButtonPressed() {
    }

    @Override
    public void onUpdate() {
    }
}
