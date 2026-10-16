// Title: Find an AprilTag by ID
// Description: Look for one tag ID in the current detections.
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

AprilTagDetection desiredTag = null;
for (AprilTagDetection detection : ${1:aprilTag}.getDetections()) {
    if (detection.metadata != null && detection.id == ${2:20}) {
        desiredTag = detection;
        break;
    }
}
if (desiredTag != null) {
    $0
}
