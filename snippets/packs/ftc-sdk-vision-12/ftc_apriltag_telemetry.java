// Title: AprilTag telemetry
// Description: Show each detected tag's ID and position.
import java.util.List;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

List<AprilTagDetection> currentDetections = ${1:aprilTag}.getDetections();
telemetry.addData("# AprilTags Detected", currentDetections.size());
for (AprilTagDetection detection : currentDetections) {
    if (detection instanceof AprilTagSingleDetection) {
        AprilTagSingleDetection tag = (AprilTagSingleDetection) detection;
        if (tag.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", tag.id, tag.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", tag.ftcPose.range, tag.ftcPose.bearing, tag.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", tag.id));
        }
    }
}
