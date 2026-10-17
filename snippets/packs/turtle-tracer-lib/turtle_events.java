// Title: Turtle Tracer path events
// Description: Run actions part-way along a path, after a delay, or when the robot gets near a pose.
import com.turtletracerlib.pathing.ProgressTracker;

ProgressTracker ${1:tracker} = new ProgressTracker(follower, telemetry);
${1:tracker}.setCurrentPath(${2:path});

${1:tracker}.onParametric(${3:0.75}, () -> {
            ${4:// Runs 75% along the path.}
        })
        .onTemporal(${5:500}, () -> {
            ${6:// Runs 500 ms after the path starts.}
        })
        .onSpatial(${7:targetPose}, ${8:2.0}, () -> {
            ${9:// Runs within 2 inches of the target pose.}
        });

// In the loop, after follower.update():
// ${1:tracker}.update();
