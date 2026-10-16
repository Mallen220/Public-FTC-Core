// Title: AprilTag telemetry
// Description: Show each detected tag's ID and position.
import java.util.List;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

List<AprilTagDetection> currentDetections = ${1:aprilTag}.getDetections();
telemetry.addData("# AprilTags Detected", currentDetections.size());
for (AprilTagDetection detection : currentDetections) {
    if (detection.metadata != null) {
        telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
        telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
        telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
    } else {
        telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
    }
}
