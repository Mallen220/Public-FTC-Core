// Title: Digital channel (limit switch)
// Description: Read a digital input. REV touch sensors read false when pressed.
import com.qualcomm.robotcore.hardware.DigitalChannel;

DigitalChannel ${1:limitSwitch} = hardwareMap.get(DigitalChannel.class, "${2:limit_switch}");
${1:limitSwitch}.setMode(DigitalChannel.Mode.INPUT);
boolean pressed = !${1:limitSwitch}.getState();
