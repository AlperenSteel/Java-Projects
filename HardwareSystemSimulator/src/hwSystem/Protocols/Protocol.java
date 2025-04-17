package hwSystem.Protocols;

import java.util.Stack;
/**
 * Interface representing a communication protocol used by devices.
 * Examples include I2C, SPI, UART, and OneWire.
 */
public interface Protocol {
    /**
     * Simulates reading data from the protocol.
     *
     * @return A string indicating that a read operation was performed.
     */
    String read();
    /**
     * Writes data to the protocol for communication.
     *
     * @param data The data string to write.
     */
    void write(String data);
    /**
     * Returns the name of the protocol.
     *
     * @return The protocol name (e.g., "I2C").
     */
    String getProtocolName();
    /**
     * Logs an entry to the protocol’s log stack.
     *
     * @param entry The log entry to store.
     */
    void log(String entry);
    /**
     * Returns the stack of log entries for the protocol.
     *
     * @return The log stack.
     */
    Stack<String> getLogs();
    /**
     * Sets the port ID associated with the protocol.
     *
     * @param id The port ID to set.
     */
    void setPortID(int id);
    /**
     * Returns the port ID of the protocol.
     *
     * @return The current port ID.
     */
    int getPortID();
}
