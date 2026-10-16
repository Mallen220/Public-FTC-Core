// Title: Find an AprilTag by ID
// Description: Look for one tag ID in the current detections.
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

AprilTagSingleDetection desiredTag = null;
for (AprilTagDetection detection : ${1:aprilTag}.getDetections()) {
    if (detection instanceof AprilTagSingleDetection && ((AprilTagSingleDetection) detection).id == ${2:20}) {
        desiredTag = (AprilTagSingleDetection) detection;
        break;
    }
}
if (desiredTag != null) {
    $0
}
