package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents the PCA9685 motor driver device.
 * This device allows motor control over the I2C protocol.
 */
public class PCA9685 extends MotorDriver {
    /**
     * Constructs a PCA9685 motor driver using the specified protocol.
     *
     * @param protocol The protocol used for communication (e.g., I2C).
     */
    public PCA9685(Protocol protocol) {
        super(protocol);
    }
    /**
     * Returns the name of the device.
     *
     * @return The string "PCA9685".
     */
    @Override
    public String getName() {
        return "PCA9685";
    }
    /**
    * Turns the motor driver ON if currently OFF.
    * Logs the action to the protocol.
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
     * Turns the motor driver ON if currently OFF.
     * Logs the action to the protocol.
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
     * Sets the motor speed and logs the value.
     *
     * @param speed The speed to be set for the motor.
     */
    @Override
    public void setMotorSpeed(int speed) {
        protocol.write("setMotorSpeed " + speed);
        System.out.println(getName() + ": Setting speed to " + speed + ".");
    }
}
