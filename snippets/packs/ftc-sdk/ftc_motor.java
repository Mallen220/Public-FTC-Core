// Title: Motor
// Description: Declare a motor and get it from the hardware map.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

DcMotor ${1:motor} = hardwareMap.get(DcMotor.class, "${2:motor}");
${1:motor}.setDirection(DcMotorSimple.Direction.${3|FORWARD,REVERSE|});
${1:motor}.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.${4|BRAKE,FLOAT|});
