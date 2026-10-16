// Title: Color sensor
// Description: Read a color sensor as red, green, blue and alpha, and as hue, saturation and value.
import android.graphics.Color;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

NormalizedColorSensor ${1:colorSensor} = hardwareMap.get(NormalizedColorSensor.class, "${2:color}");
${1:colorSensor}.setGain(${3:2});
if (${1:colorSensor} instanceof SwitchableLight) {
    ((SwitchableLight) ${1:colorSensor}).enableLight(true);
}

NormalizedRGBA colors = ${1:colorSensor}.getNormalizedColors();
float[] hsv = new float[3];
Color.colorToHSV(colors.toColor(), hsv);
telemetry.addData("Red", "%.3f", colors.red);
telemetry.addData("Green", "%.3f", colors.green);
telemetry.addData("Blue", "%.3f", colors.blue);
telemetry.addData("Alpha", "%.3f", colors.alpha);
telemetry.addData("Hue", "%.3f", hsv[0]);
telemetry.addData("Saturation", "%.3f", hsv[1]);
telemetry.addData("Value", "%.3f", hsv[2]);
