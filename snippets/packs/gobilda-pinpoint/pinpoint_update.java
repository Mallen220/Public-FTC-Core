// Title: Pinpoint read pose
// Description: Update the Pinpoint once per loop and read the robot's pose.
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

${1:pinpoint}.update();
Pose2D pose = ${1:pinpoint}.getPosition();
double x = pose.getX(DistanceUnit.INCH);
double y = pose.getY(DistanceUnit.INCH);
double heading = pose.getHeading(AngleUnit.DEGREES);
