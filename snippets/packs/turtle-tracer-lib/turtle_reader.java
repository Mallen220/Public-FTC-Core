// Title: Turtle Tracer reader
// Description: Load a .turt file from the AutoPaths assets folder and read a pose from it.
import com.pedropathing.math.Pose;
import com.turtletracerlib.TurtleTracerReader;
import java.io.IOException;

TurtleTracerReader ${1:reader};
try {
    ${1:reader} = new TurtleTracerReader("${2:AutoRoutine.turt}", hardwareMap.appContext);
} catch (IOException e) {
    throw new RuntimeException("Could not load ${2:AutoRoutine.turt} from assets/AutoPaths", e);
}
Pose ${3:startPose} = ${1:reader}.get("${4:startPoint}");
