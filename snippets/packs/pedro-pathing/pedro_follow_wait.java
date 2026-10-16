// Title: Pedro follow and wait (LinearOpMode)
// Description: Follow a path and wait for it to finish.

follower.follow(${1:path});
while (opModeIsActive() && follower.isBusy()) {
    follower.update();
}
