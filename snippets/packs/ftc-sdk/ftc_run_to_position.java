// Title: Motor run to position
// Description: Move a motor to an encoder target and wait for it.
import com.qualcomm.robotcore.hardware.DcMotor;

${1:motor}.setTargetPosition(${2:target});
${1:motor}.setMode(DcMotor.RunMode.RUN_TO_POSITION);
${1:motor}.setPower(${3:0.5});
while (opModeIsActive() && ${1:motor}.isBusy()) {
    telemetry.addData("Position", ${1:motor}.getCurrentPosition());
    telemetry.addData("Target", ${1:motor}.getTargetPosition());
    telemetry.update();
}
${1:motor}.setPower(0);
$0
