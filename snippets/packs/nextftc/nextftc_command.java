// Title: NextFTC Command
// Description: A command class with start, update, stop and isDone.
import dev.nextftc.core.commands.Command;

public class ${TM_FILENAME_BASE} extends Command {
    public ${TM_FILENAME_BASE}() {
        requires(${1:subsystem});
    }

    @Override
    public boolean isDone() {
        return ${2:false};
    }

    @Override
    public void start() {
        $0
    }

    @Override
    public void update() {
    }

    @Override
    public void stop(boolean interrupted) {
    }
}
