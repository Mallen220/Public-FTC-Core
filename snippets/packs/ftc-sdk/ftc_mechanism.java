// Title: Mechanism class with a hardware map
// Description: A class that gets its hardware from the HardwareMap in an init method.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ${TM_FILENAME_BASE} {
    private ${1:DcMotor} ${2:motor};

    public void init(HardwareMap hardwareMap) {
        ${2:motor} = hardwareMap.get(${1:DcMotor}.class, "${3:motor}");
        $0
    }
}
