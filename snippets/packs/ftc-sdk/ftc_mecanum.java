// Title: Mecanum drive (robot-centric)
// Description: Drive a mecanum robot from the left stick and right stick x.

double axial = -gamepad1.left_stick_y;
double lateral = gamepad1.left_stick_x;
double yaw = gamepad1.right_stick_x;

double denominator = Math.max(Math.abs(axial) + Math.abs(lateral) + Math.abs(yaw), 1);
${1:leftFront}.setPower((axial + lateral + yaw) / denominator);
${2:leftBack}.setPower((axial - lateral + yaw) / denominator);
${3:rightFront}.setPower((axial - lateral - yaw) / denominator);
${4:rightBack}.setPower((axial + lateral - yaw) / denominator);
