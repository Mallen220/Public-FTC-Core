// Title: SolversLib Motor
// Description: A Motor with a RawPower run mode.
import com.seattlesolvers.solverslib.hardware.motors.Motor;

Motor ${1:motor} = new Motor(hardwareMap, "${2:motor}");
${1:motor}.setRunMode(Motor.RunMode.RawPower);
${1:motor}.setZeroPowerBehavior(Motor.ZeroPowerBehavior.${3|BRAKE,FLOAT|});
${1:motor}.setInverted(${4:false});
