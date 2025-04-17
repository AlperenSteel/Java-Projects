package hwSystem.Devices;

import hwSystem.Protocols.Protocol;
/**
 * Represents the SparkFunMD motor driver device.
 * This device allows motor control over a specific protocol.
 */
public class SparkFunMD extends MotorDriver {
    /**
     * Constructs a SparkFunMD motor driver using the specified protocol.
     *
     * @param protocol The protocol used for communication (e.g., SPI).
     */
    public SparkFunMD(Protocol protocol) {
        super(protocol);
    }
    /**
    * Returns the name of this motor driver device.
    *
    * @return The string "SparkFunMD".
    */
    @Override
    public String getName() {
        return "SparkFunMD";
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
     * Turns the motor driver OFF if currently ON.
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
