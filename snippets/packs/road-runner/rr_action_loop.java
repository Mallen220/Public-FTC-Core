// Title: Road Runner action loop
// Description: Run actions you keep in a list, one tick per loop, with dashboard telemetry.
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import java.util.ArrayList;
import java.util.List;

List<Action> runningActions = new ArrayList<>();

TelemetryPacket packet = new TelemetryPacket();
List<Action> newActions = new ArrayList<>();
for (Action action : runningActions) {
    action.preview(packet.fieldOverlay());
    if (action.run(packet)) {
        newActions.add(action);
    }
}
runningActions = newActions;
FtcDashboard.getInstance().sendTelemetryPacket(packet);
