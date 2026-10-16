// Title: SolversLib Command
// Description: A command class with initialize, execute, isFinished and end.
import com.seattlesolvers.solverslib.command.CommandBase;

public class ${TM_FILENAME_BASE} extends CommandBase {
    private final ${1:Subsystem} ${2:subsystem};

    public ${TM_FILENAME_BASE}(${1:Subsystem} ${2:subsystem}) {
        this.${2:subsystem} = ${2:subsystem};
        addRequirements(${2:subsystem});
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        $0
    }

    @Override
    public boolean isFinished() {
        return ${3:false};
    }

    @Override
    public void end(boolean interrupted) {
    }
}
