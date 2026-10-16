// Title: SolversLib PIDF to a target
// Description: Drive a motor toward a target with a PIDF controller until it arrives.
import com.seattlesolvers.solverslib.controller.PIDFController;

PIDFController ${1:controller} = new PIDFController(${2:kP}, ${3:kI}, ${4:kD}, ${5:kF});
${1:controller}.setTolerance(${6:10});
${1:controller}.setSetPoint(${7:target});
while (opModeIsActive() && !${1:controller}.atSetPoint()) {
    ${8:motor}.set(${1:controller}.calculate(${8:motor}.getCurrentPosition()));
}
${8:motor}.set(0);
