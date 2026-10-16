// Title: Pinpoint setup
// Description: Get the goBILDA Pinpoint and configure its pod offsets, encoders and starting pose.
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

GoBildaPinpointDriver ${1:pinpoint} = hardwareMap.get(GoBildaPinpointDriver.class, "${2:pinpoint}");
${1:pinpoint}.setOffsets(${3:0.0}, ${4:0.0}, DistanceUnit.MM); // X and Y offsets of the pods from the tracking point
${1:pinpoint}.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.${5|goBILDA_4_BAR_POD,goBILDA_SWINGARM_POD|});
${1:pinpoint}.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.${6|FORWARD,REVERSED|},
                                   GoBildaPinpointDriver.EncoderDirection.${7|FORWARD,REVERSED|});
${1:pinpoint}.resetPosAndIMU();
${1:pinpoint}.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
