package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Abstract base class representing an IMU (Inertial Measurement Unit) sensor.
 * This sensor provides both acceleration and rotation data.
 */
public abstract class IMUSensor extends Sensor {
    /**
     * Constructs an IMUSensor with the given communication protocol.
     *
     * @param protocol The protocol used for communication (e.g., SPI or UART).
     */
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the type of this sensor as "IMUSensor".
     *
     * @return The string "IMUSensor".
     */
    @Override
    public String getSensType() {
        return "IMUSensor";
    }
    /**
     * Returns a formatted string that combines acceleration and rotation data.
     *
     * @return A string like "Accel: 1.00, Rot: 0.50".
     */
    @Override
    public String data2String() {
        return String.format("Accel: %.2f, Rot: %.2f", getAccel(), getRot());
    }
    /**
     * Returns the current acceleration value from the sensor.
     *
     * @return A float representing acceleration.
     */
    public abstract float getAccel();
    /**
     * Returns the current rotation value from the sensor.
     *
     * @return A float representing rotation.
     */
    public abstract float getRot();
}
