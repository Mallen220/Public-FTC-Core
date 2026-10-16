// Title: IMU
// Description: Get the built-in IMU and set the hub's orientation on the robot.
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

IMU imu = hardwareMap.get(IMU.class, "imu");
imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
        RevHubOrientationOnRobot.LogoFacingDirection.${1|UP,DOWN,LEFT,RIGHT,FORWARD,BACKWARD|},
        RevHubOrientationOnRobot.UsbFacingDirection.${2|FORWARD,BACKWARD,UP,DOWN,LEFT,RIGHT|})));
