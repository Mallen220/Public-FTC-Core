// Title: Pedro field-centric drive
// Description: Drive by hand, relative to the field.
import com.pedropathing.follower.ManualDrive;

follower.manual(ManualDrive.fieldCentric(
        -gamepad1.left_stick_y,
        -gamepad1.left_stick_x,
        -gamepad1.right_stick_x,
        follower.pose().heading()));
