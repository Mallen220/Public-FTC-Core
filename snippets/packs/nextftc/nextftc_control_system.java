// Title: NextFTC control system
// Description: A control system with a position PID.
import dev.nextftc.control.ControlSystem;

private final ControlSystem ${1:controller} = ControlSystem.builder()
        .posPid(${2:0.01}, ${3:0.0}, ${4:0.0})
        .build();
