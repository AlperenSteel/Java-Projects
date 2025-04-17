package hwSystem.Devices;

import java.util.Stack;
import hwSystem.Protocols.Protocol;
/**
 * Abstract base class representing wireless communication devices.
 * Devices such as Bluetooth and Wifi extend this class.
 */
public abstract class WirelessIO extends Device {
    /**
     * Internal stack used to store messages for sending and receiving operations.
     */
    protected Stack<String> messages = new Stack<>();
    /**
     * Constructs a WirelessIO device using the specified protocol.
     *
     * @param protocol The communication protocol (e.g., UART, SPI).
     */
    public WirelessIO(Protocol protocol) {
        super(protocol);
    }
    /**
     * Sends data through the wireless connection.
     *
     * @param data The message to be sent.
     */
    public abstract void sendData(String data);
    /**
     * Receives the last sent message from the wireless connection.
     *
     * @return The last message received or "null" if no message exists.
     */
    public abstract String recvData();
    /**
     * Returns the device type as a string.
     *
     * @return The string "WirelessIO".
     */
    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}
