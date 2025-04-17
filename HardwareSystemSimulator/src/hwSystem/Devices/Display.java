package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a generic display device that can print messages.
 * This is an abstract class and should be extended by specific display implementations.
 */
public abstract class Display extends Device {
    /**
     * Constructs a Display device using the given communication protocol.
     *
     * @param protocol The protocol used to communicate with the display.
     */
    public Display(Protocol protocol) {
        super(protocol);
    }
    /**
     * Abstract method to print a given message to the display.
     *
     * @param message The message to be displayed.
     */
    public abstract void printData(String message);
    /**
     * Returns the type of the device.
     *
     * @return The string "Display".
     */
    @Override
    public String getDevType() {
        return "Display";
    }
}
