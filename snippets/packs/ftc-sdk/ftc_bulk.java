// Title: Bulk reads
// Description: Cache hub reads so each loop talks to the hubs less. In MANUAL mode, clear the cache at the top of every loop.
import com.qualcomm.hardware.lynx.LynxModule;
import java.util.List;

List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
for (LynxModule hub : allHubs) {
    hub.setBulkCachingMode(LynxModule.BulkCachingMode.${1|AUTO,MANUAL|});
}
