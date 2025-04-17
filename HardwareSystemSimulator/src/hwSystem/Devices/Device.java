package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Abstract base class representing a generic device in the system.
 * All specific device types (e.g., sensors, displays) inherit from this class.
 */
public abstract class Device {
    /**
     * The communication protocol used by the device.
     */
    protected Protocol protocol;
    /**
     * The current state of the device (ON or OFF).
     */
    protected State state;
    /**
     * Constructs a device with the specified communication protocol.
     * Devices are OFF by default upon creation.
     *
     * @param protocol The protocol used by the device.
     */
    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF;
    }
    /**
     * Returns the current ON/OFF state of the device.
     *
     * @return The state of the device.
     */
    public State getState() {
        return state;
    }
    /**
     * Returns the communication protocol associated with the device.
     *
     * @return The protocol used by the device.
     */
    public Protocol getProtocol() {
        return protocol;
    }
    /**
     * Returns the name of the device.
     *
     * @return A string representing the name.
     */
    public abstract String getName();
    /**
     * Returns the type of the device.
     *
     * @return A string representing the device type.
     */
    public abstract String getDevType();
    /**
     * Turns the device ON.
     */
    public abstract void turnON();
    /**
     * Turns the device OFF.
     */
    public abstract void turnOFF();
    /**
     * Enum representing device power state.
     */
    public enum State {
        /** Device is turned ON. */
        ON, 
        /** Device is turned OFF. */
        OFF;
    }
}
