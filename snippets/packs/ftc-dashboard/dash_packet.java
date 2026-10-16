// Title: Dashboard packet
// Description: Send a value and a circle on the field view in one telemetry packet.
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

TelemetryPacket packet = new TelemetryPacket();
packet.put("${1:label}", ${2:value});
Canvas field = packet.fieldOverlay();
field.setStroke("${3:#3F51B5}");
field.strokeCircle(${4:x}, ${5:y}, ${6:9});
FtcDashboard.getInstance().sendTelemetryPacket(packet);
