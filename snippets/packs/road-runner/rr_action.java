// Title: Road Runner Action class
// Description: A custom Action. run returns true while it should keep running.
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import androidx.annotation.NonNull;

public class ${TM_FILENAME_BASE} implements Action {
    private boolean initialized = false;

    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        if (!initialized) {
            $0
            initialized = true;
        }
        // Return true to keep running, false when finished.
        return ${1:false};
    }
}
