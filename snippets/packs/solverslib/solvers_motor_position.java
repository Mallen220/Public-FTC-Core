// Title: SolversLib Motor position control
// Description: Move a motor to an encoder position.
import com.seattlesolvers.solverslib.hardware.motors.Motor;

${1:motor}.setRunMode(Motor.RunMode.PositionControl);
${1:motor}.setPositionCoefficient(${2:0.05});
${1:motor}.setPositionTolerance(${3:10});
${1:motor}.setTargetPosition(${4:target});
${1:motor}.set(${5:0.5});
