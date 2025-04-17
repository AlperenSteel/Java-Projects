package hwSystem.Protocols;

import java.util.Stack;
/**
 * Represents the UART (Universal Asynchronous Receiver-Transmitter) communication protocol.
 * Used for serial communication between devices.
 */
public class UART implements Protocol {
    private Stack<String> logStack;
    private int portID;
    /**
     * Constructs a UART protocol and initializes the log stack with "Port Opened."
     */
    public UART() {
        logStack = new Stack<>();
        logStack.push("Port Opened.");
    }
    /**
     * Logs a read operation and returns a description string.
     *
     * @return The string indicating a read occurred.
     */
    @Override
    public String read() {
        log("Reading.");
        return getProtocolName() + ":Reading.";
    }
    /**
     * Logs a write operation to the protocol.
     *
     * @param data The data to be written.
     */
    @Override
    public void write(String data) {
        if (data == null || data.isEmpty()) {
            System.err.printf("%s: ERROR - Cannot write empty data.\n", getProtocolName());
            return;
        }
        /*log("Writing \"" + data + "\".");*/
        log(data);
    }
    /**
     * Returns the name of this protocol.
     *
     * @return The string "UART".
     */
    @Override
    public String getProtocolName() {
        return "UART";
    }
    /**
     * Pushes a log entry onto the internal log stack.
     *
     * @param entry The log message to push.
     */
    @Override
    public void log(String entry) {
        logStack.push(entry);
    }
    /**
     * Returns the protocol’s log stack.
     *
     * @return The stack of log messages.
     */
    @Override
    public Stack<String> getLogs() {
        return logStack;
    }
    /**
     * Sets the port ID for this protocol.
     *
     * @param id The port ID.
     */
    @Override
    public void setPortID(int id) {
        this.portID = id;
    }
    /**
     * Gets the port ID of this protocol.
     *
     * @return The port ID.
     */
    @Override
    public int getPortID() {
        return portID;
    }
}
