package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents an MPU6050 IMU sensor device.
 * Provides both acceleration and rotation data.
 */
public class MPU6050 extends IMUSensor {
    /**
     * Constructs an MPU6050 sensor with the specified protocol.
     *
     * @param protocol The communication protocol (e.g., I2C).
     */
    public MPU6050(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "MPU6050".
     */
    @Override
    public String getName() {
        return "MPU6050";
    }
    /**
     * Turns the MPU6050 sensor ON if currently OFF.
     * Logs the operation.
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
     * Turns the MPU6050 sensor OFF if currently ON.
     * Logs the operation.
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
     * Returns simulated acceleration data.
     *
     * @return A float representing acceleration.
     */
    @Override
    public float getAccel() {
        return 1.00f;
    }
    /**
     * Returns simulated rotation data.
     *
     * @return A float representing rotation.
     */
    @Override
    public float getRot() {
        return 0.50f;
    }
    /**
     * Reads the sensor and returns a formatted output string.
     *
     * @return A string including device name, type, and data.
     */
    @Override
    public String readSensor() {
        protocol.read();
        return getName() + " " + getDevType() + ": " + data2String() + ".";
    }
}
