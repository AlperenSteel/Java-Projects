package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents an LCD display device.
 * This class can print data when turned ON.
 */
public class LCD extends Display {
    /**
     * Constructs an LCD display with the specified protocol.
     *
     * @param protocol The communication protocol (e.g., I2C).
     */
    public LCD(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "LCD".
     */
    @Override
    public String getName() {
        return "LCD";
    }
    /**
     * Turns the LCD display ON if currently OFF.
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
     * Turns the LCD display OFF if currently ON.
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
     * Prints data on the LCD display.
     *
     * @param data The string to be printed.
     */
    @Override
    public void printData(String data) {
        protocol.write("printDisplay " + data);
        System.out.println(getName() + ": Printing \\\"" + data + "\\\".");
    }
}
