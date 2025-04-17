package hwSystem.Protocols;

import java.util.Stack;
/**
* Represents the I2C communication protocol implementation.
* Maintains a log of actions using a stack.
*/
public class I2C implements Protocol {
    private Stack<String> logStack;
    private int portID;
    /**
    * Constructs an I2C protocol and initializes the log stack with "Port Opened."
    */
    public I2C() {
        logStack = new Stack<>();
        logStack.push("Port Opened.");
    }
    /**
    * Logs a read operation and returns a description.
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
    * Logs a write operation to the protocol.
    *
    * @return data The data to be written.
    */
    @Override
    public String getProtocolName() {
        return "I2C";
    }
    /**
    * Pushes a new log entry to the protocol's log stack.
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
    * Sets the protocol's port ID.
    *
    * @param id The port ID to set.
    */
    @Override
    public void setPortID(int id) {
        this.portID = id;
    }
    /**
    * Gets the current port ID of the protocol.
    *
    * @return The port ID.
    */
    @Override
    public int getPortID() {
        return portID;
    }
}
