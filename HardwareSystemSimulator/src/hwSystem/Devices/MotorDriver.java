package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents a generic motor driver device capable of controlling motor speed.
 * This is an abstract class to be extended by specific motor driver implementations.
 */
public abstract class MotorDriver extends Device {
    /**
    * Constructs a MotorDriver with the given communication protocol.
    *
    * @param protocol The protocol used to control the motor driver.
    */
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the type of the device.
     *
     * @return The string "MotorDriver".
     */
    @Override
    public String getDevType() {
        return "MotorDriver";
    }
    /**
     * Abstract method to set the speed of the motor.
     *
     * @param speed The speed value to be set.
     */
    public abstract void setMotorSpeed(int speed);
}
