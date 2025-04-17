package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a BME280 temperature sensor device.
 * This class extends TempSensor and simulates temperature readings over a given protocol.
 */
public class BME280 extends TempSensor {
    /**
     * Constructs a BME280 sensor using the given communication protocol.
     *
     * @param protocol The communication protocol (e.g., I2C, SPI).
     */
    public BME280(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "BME280".
     */
    @Override
    public String getName() {
        return "BME280";
    }
    /**
     * Turns the sensor ON if it is currently OFF.
     * Logs the action via the protocol.
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
     * Turns the sensor OFF if it is currently ON.
     * Logs the action via the protocol.
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
     * Simulates returning the temperature from the sensor.
     *
     * @return Simulated temperature value.
     */
    @Override
    public float getTemp() {
        return 24.00f;
    }
    /**
     * Reads data from the sensor by calling protocol read and returning formatted output.
     *
     * @return A formatted string containing device name, type, and temperature reading.
     */
    @Override
    public String readSensor() {
        protocol.read();
        return getName() + " " + getDevType() + ": " + data2String() + ".";
    }
    /**
     * Converts sensor data to a readable string.
     *
     * @return A string showing simulated temperature in Celsius.
     */
    @Override
    public String data2String() {
    return getName() + " " + getDevType() + ": Temp: 0.92C.";
    }
}
