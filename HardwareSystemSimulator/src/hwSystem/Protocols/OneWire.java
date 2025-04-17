package hwSystem.Protocols;

import java.util.Stack;
/**
* Represents the OneWire communication protocol.
* Used for simple, low-speed communication with devices like temperature sensors.
*/
public class OneWire implements Protocol {
    private Stack<String> logStack;
    private int portID;
    /**
    * Constructs a OneWire protocol instance and initializes the log stack.
    */
    public OneWire() {
        logStack = new Stack<>();
        logStack.push("Port Opened.");
    }

    @Override
    public String read() {
        log("Reading.");
        return getProtocolName() + ":Reading.";
    }

    @Override
    public void write(String data) {
        if (data == null || data.isEmpty()) {
            System.err.printf("%s: ERROR - Cannot write empty data.\n", getProtocolName());
            return;
        }
        /*log("Writing \"" + data + "\".");*/
        log(data);
    }

    @Override
    public String getProtocolName() {
        return "OneWire";
    }

    @Override
    public void log(String entry) {
        logStack.push(entry);
    }

    @Override
    public Stack<String> getLogs() {
        return logStack;
    }

    @Override
    public void setPortID(int id) {
        this.portID = id;
    }

    @Override
    public int getPortID() {
        return portID;
    }
}
