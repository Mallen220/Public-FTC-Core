// Title: Ivy custom command
// Description: A command built from start, execute, done and end callbacks that claims a subsystem while it runs.
import com.pedropathing.ivy.CommandBuilder;

CommandBuilder ${1:command} = new CommandBuilder()
        .setStart(() -> {
            ${2:// Runs once when the command starts.}
        })
        .setExecute(() -> {
            ${3:// Runs every loop while the command is running.}
        })
        .setDone(() -> ${4:true})
        .setEnd(endCondition -> {
            ${5:// Runs when the command finishes or is interrupted.}
        })
        .requiring(${6:subsystem});
