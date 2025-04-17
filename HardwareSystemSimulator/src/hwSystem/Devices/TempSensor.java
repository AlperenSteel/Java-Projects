package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Abstract base class for temperature sensor devices.
 * Devices such as DHT11 and BME280 extend this class.
 */
public abstract class TempSensor extends Sensor {
    /**
     * Constructs a temperature sensor with the given communication protocol.
     *
     * @param protocol The protocol used to communicate with the device.
     */
    public TempSensor(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the type of sensor.
     *
     * @return The string "TempSensor".
     */
    @Override
    public String getSensType() {
        return "TempSensor";
    }
    /**
     * Returns the current temperature reading from the sensor.
     *
     * @return Temperature as a float value in Celsius.
     */
    public abstract float getTemp();
}
