// Title: AprilTag detection
// Description: Start the AprilTag processor on a webcam.
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

AprilTagProcessor ${1:aprilTag} = AprilTagProcessor.easyCreateWithDefaults();
VisionPortal ${2:visionPortal} = VisionPortal.easyCreateWithDefaults(
        hardwareMap.get(WebcamName.class, "${3:Webcam 1}"), ${1:aprilTag});
