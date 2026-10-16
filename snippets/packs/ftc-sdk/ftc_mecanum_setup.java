// Title: Mecanum motors setup
// Description: Get four drive motors and set the direction of each side.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

DcMotor ${1:leftFront} = hardwareMap.get(DcMotor.class, "${2:left_front}");
DcMotor ${3:leftBack} = hardwareMap.get(DcMotor.class, "${4:left_back}");
DcMotor ${5:rightFront} = hardwareMap.get(DcMotor.class, "${6:right_front}");
DcMotor ${7:rightBack} = hardwareMap.get(DcMotor.class, "${8:right_back}");

${1:leftFront}.setDirection(DcMotorSimple.Direction.${9|REVERSE,FORWARD|});
${3:leftBack}.setDirection(DcMotorSimple.Direction.$9);
${5:rightFront}.setDirection(DcMotorSimple.Direction.${10|FORWARD,REVERSE|});
${7:rightBack}.setDirection(DcMotorSimple.Direction.$10);
$0
