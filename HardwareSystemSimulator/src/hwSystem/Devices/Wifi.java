package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a WiFi wireless communication device.
 * This class extends WirelessIO and supports sending and receiving data.
 */
public class Wifi extends WirelessIO {
    /**
     * Constructs a Wifi device with the specified communication protocol.
     *
     * @param protocol The protocol used by this WiFi device (e.g., SPI or UART).
     */
    public Wifi(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "Wifi".
     */
    @Override
    public String getName() {
        return "Wifi";
    }
    /**
     * Turns the WiFi device ON if it's currently OFF.
     * Also logs the action to the associated protocol.
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
     * Turns the WiFi device OFF if it's currently ON.
     * Also logs the action to the associated protocol.
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
     * Sends data over the WiFi connection.
     * Logs the message and pushes it onto the internal message stack.
     *
     * @param data The data to send.
     */
    @Override
    public void sendData(String data) {
        protocol.write("Writing \\\"" + data + "\\\".");
        messages.push(data);
        System.out.println(getName() + ": Sending \"" + data + "\".");
    }
    /**
     * Receives the most recently sent message over WiFi.
     * Pops the message from the internal message stack.
     *
     * @return The received message, or "null" if no message is available.
     */
    @Override
    public String recvData() {
        protocol.read();
        String response;
        try {
            response = messages.pop();
        } catch (Exception e) {
            response = "null";
        }
        System.out.println(getName() + ": Received \"" + response + "\".");
        return response;
    }
}
