// Title: SolversLib Autonomous (CommandOpMode)
// Description: An Autonomous that schedules a sequence of commands.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

@Autonomous(name = "${1:${TM_FILENAME_BASE}}", group = "${2:Auto}")
public class ${TM_FILENAME_BASE} extends CommandOpMode {
    @Override
    public void initialize() {
        schedule(new SequentialCommandGroup(
                $0
        ));
    }
}
