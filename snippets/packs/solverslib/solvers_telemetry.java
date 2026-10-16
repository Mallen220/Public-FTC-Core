// Title: SolversLib Telemetry data
// Description: Collect telemetry and send it in one call.
import com.seattlesolvers.solverslib.util.TelemetryData;

TelemetryData ${1:telemetryData} = new TelemetryData(telemetry);
${1:telemetryData}.addData("${2:Label}", ${3:value});
${1:telemetryData}.update();
