// Title: NextFTC Subsystem
// Description: A subsystem as a singleton. initialize runs at init and periodic every loop.
import dev.nextftc.core.subsystems.Subsystem;

public class ${TM_FILENAME_BASE} implements Subsystem {
    public static final ${TM_FILENAME_BASE} INSTANCE = new ${TM_FILENAME_BASE}();

    private ${TM_FILENAME_BASE}() {
    }

    @Override
    public void initialize() {
        $0
    }

    @Override
    public void periodic() {
    }
}
