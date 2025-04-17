package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Abstract base class for all sensor devices in the system.
 * Sensors can be temperature sensors or IMU sensors, and provide readable data.
 */
public abstract class Sensor extends Device {
    /**
     * Constructs a sensor with the specified communication protocol.
     *
     * @param protocol The communication protocol used by the sensor.
     */
    public Sensor(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the specific sensor type (e.g., "TempSensor", "IMUSensor").
     *
     * @return A string indicating the sensor type.
     */
    public abstract String getSensType();        // "TempSensor", "IMUSensor"
    /**
     * Returns the sensor data as a formatted string for display or logging.
     *
     * @return Formatted string with sensor data.
     */
    public abstract String data2String();        // "Temperature: ..." or "Accel: ... Rot: ..."
    /**
     * Returns the full device type including sensor classification.
     *
     * @return A string like "TempSensor Sensor".
     */
    @Override
    public String getDevType() {
        return getSensType() + " Sensor";
    }
    /**
     * Reads the sensor using the associated protocol.
     * May log the operation or return formatted data.
     *
     * @return A string containing the sensor's reading.
     */
    public abstract String readSensor(); 
}
