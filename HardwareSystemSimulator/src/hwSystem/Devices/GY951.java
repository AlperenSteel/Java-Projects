package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a GY951 IMU sensor device.
 * This sensor provides both acceleration and rotation data.
 */
public class GY951 extends IMUSensor {
    /**
     * Constructs a GY951 sensor with the given communication protocol.
     *
     * @param protocol The protocol used for communication (e.g., SPI or UART).
     */
    public GY951(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "GY951".
     */
    @Override
    public String getName() {
        return "GY951";
    }
    /**
     * Turns the GY951 sensor ON if it is currently OFF.
     * Logs the action through the protocol.
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
     * Turns the GY951 sensor OFF if it is currently ON.
     * Logs the action through the protocol.
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
     * Simulates acceleration data reading from the GY951 sensor.
     *
     * @return A simulated acceleration value.
     */
    @Override
    public float getAccel() {
        return 1.00f;
    }
    /**
     * Simulates rotation data reading from the GY951 sensor.
     *
     * @return A simulated rotation value.
     */
    @Override
    public float getRot() {
        return 0.50f;
    }
    /**
     * Reads the sensor and returns a formatted string.
     *
     * @return A string with the sensor name, type, and formatted data.
     */
    @Override
    public String readSensor() {
        protocol.read();
        return getName() + " " + getDevType() + ": " + data2String() + ".";
    }
}
