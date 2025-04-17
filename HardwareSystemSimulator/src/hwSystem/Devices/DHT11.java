package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a DHT11 temperature sensor device.
 * This class extends the TempSensor abstract class and uses a communication protocol.
 */
public class DHT11 extends TempSensor {
    /**
     * Constructs a DHT11 sensor object with the specified communication protocol.
     *
     * @param protocol The communication protocol used by this device (e.g., OneWire).
     */
    public DHT11(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "DHT11".
     */
    @Override
    public String getName() {
        return "DHT11";
    }
    /**
     * Turns the device ON if it is currently OFF.
     * Also logs the action to the associated protocol.
     */
    @Override
    public void turnON() {
        if (state == State.OFF) {
            protocol.write("Writing \"turnON\".");
            state = State.ON;
            System.out.println(getName() + ": Turning ON.");
        }
    }
    /**
     * Turns the device OFF if it is currently ON.
     * Also logs the action to the associated protocol.
     */
    @Override
    public void turnOFF() {
        if (state == State.ON) {
            protocol.write("Writing \"turnOFF\".");
            state = State.OFF;
            System.out.println(getName() + ": Turning OFF.");
        }
    }
    /**
     * Returns a formatted temperature data string.
     *
     * @return Formatted temperature output.
     */
    @Override
    public float getTemp() {
        return 24.00f; // Simulated temperature value
    }
    /**
     * Reads the sensor data using the protocol's read method.
     * This method simulates reading from the sensor and returns formatted output.
     *
     * @return A string that includes the device name, type, and sensor data.
     */
    @Override
    public String readSensor() {
        protocol.read();
        return getName() + " " + getDevType() + ": " + data2String() + ".";
    }
    /**
     * Converts the sensor's data into a formatted string.
     * Used for display or logging purposes.
     *
     * @return A string representing the temperature value in Celsius.
     */
    @Override
    public String data2String() {
    return getName() + " " + getDevType() + ": Temp: 0.92C.";
}
}

