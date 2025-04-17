package hwSystem;
/**
 * The hwSystem class simulates a hardware control system that manages multiple types of devices
 * (Sensors, Displays, WirelessIOs, and MotorDrivers) through configurable communication ports.
 *
 * <p>This class supports the following features:
 * <ul>
 *     <li>Loading configuration from a file</li>
 *     <li>Adding/removing devices to/from ports</li>
 *     <li>Handling turnON/turnOFF operations</li>
 *     <li>Executing device-specific commands (readSensor, printDisplay, etc.)</li>
 *     <li>Listing connected devices and ports</li>
 *     <li>Maintaining operation logs via protocol classes</li>
 * </ul>
 *
 * <p>Supported protocols include: I2C, SPI, UART, and OneWire.
 * Devices communicate via their assigned protocol instances.
 * The system reads commands from standard input and executes them in order.
 *
 * <p>All operations are designed to comply with the project restrictions:
 * <ul>
 *     <li>Only O(1) operations allowed</li>
 *     <li>No for-each loops</li>
 *     <li>Only specified data structures (ArrayList, LinkedList, Stack, Queue)</li>
 *     <li>Iterator-only traversal for ArrayLists/LinkedLists</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 * java Main.Main config.txt logs/ &lt; scenario.txt &gt; output.txt
 * </pre>
 *
 * @author Alperen
 * @version 3.0
 */
import hwSystem.Devices.*;
import hwSystem.Protocols.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;
/**
* The main class that manages the entire hardware system.
* It handles device management, protocol communication, and command execution.
*/
public class hwSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;

    private int maxSensors;
    private int maxDisplays;
    private int maxWirelessIOs;
    private int maxMotorDrivers;

    private ArrayList<Sensor> sensors;
    private ArrayList<Display> displays;
    private ArrayList<WirelessIO> wirelessIOs;
    private ArrayList<MotorDriver> motorDrivers;
    
    private Queue<String> commandQueue;
    private String logDirectory;
    /**
     * Constructs a hwSystem instance and initializes the log directory path.
     *
     * @param logDirectory The directory where protocol logs will be stored.
     */
    public hwSystem(String logDirectory) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();
        sensors = new ArrayList<>();
        displays = new ArrayList<>();
        wirelessIOs = new ArrayList<>();
        motorDrivers = new ArrayList<>();
        commandQueue = new LinkedList<>();
        this.logDirectory = logDirectory;
    }
/**
 * Loads the hardware system configuration from a file.
 * Parses the number and type of devices and initializes protocol ports accordingly.
 *
 * @param configFilePath Path to the configuration text file.
 */
public void loadConfiguration(String configFilePath) {
    try {
        File file = new File(configFilePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.startsWith("Port Configuration:")) {
                String[] portArray = line.split(":")[1].trim().split(",");
                List<String> portList = Arrays.asList(portArray);
                Iterator<String> portIt = portList.iterator();
                int portID = 0;
                while (portIt.hasNext()) {
                    String port = portIt.next().trim();

                    Protocol protocol = null;
                    switch (port) {
                        case "I2C":
                            protocol = new I2C();
                            break;
                        case "SPI":
                            protocol = new SPI();
                            break;
                        case "UART":
                            protocol = new UART();
                            break;
                        case "OneWire":
                            protocol = new OneWire();
                            break;
                        default:
                            System.err.println("Error: Unknown protocol in configuration: " + port);
                    }
                    if (protocol != null) {
                        protocol.setPortID(portID);
                        ports.add(protocol);
                        portID++;
                    }
                }
            } else if (line.startsWith("# of sensors:")) {
                maxSensors = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("# of displays:")) {
                maxDisplays = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("# of wireless adapters:")) {
                maxWirelessIOs = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("# of motor drivers:")) {
                maxMotorDrivers = Integer.parseInt(line.split(":")[1].trim());
            }
        }
        scanner.close();

    } catch (FileNotFoundException e) {
        System.err.println("Error: Configuration file not found.");
    }
}
    /**
    * Reads commands from standard input and stores them in a queue for execution.
    * This method reads until EOF is reached.
    */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().trim();
            commandQueue.add(command);
    
            if (command.equals("exit")) {
                break;
            }
        }
        scanner.close();
    }
    /**
    * Executes all queued commands one by one by dispatching to corresponding handlers.
    * Commands include device operations such as addDev, turnON, readSensor, etc.
    */
    public void runCommands() {
        while (true) {
            String command = commandQueue.poll(); // sadece poll kullanılıyor
            if (command == null) break; // Queue boşsa çık
    
            String[] parts = command.trim().split("\\s+");
            if (parts.length == 0) continue;
    
            switch (parts[0]) {
                case "turnON":
                    handleTurnON(parts);
                    break;
                case "turnOFF":
                    handleTurnOFF(parts);
                    break;
                case "addDev":
                    handleAddDev(parts);
                    break;
                case "list":
                    handleList(parts);
                    break;
                case "rmDev":
                    handleRmDev(parts);
                    break;
                case "readSensor":
                    handleReadSensor(parts);
                    break;
                case "printDisplay":
                    handlePrintDisplay(parts);
                    break;
                case "readWireless":
                    handleReadWireless(parts);
                    break;
                case "writeWireless":
                    handleWriteWireless(parts);
                    break;
                case "setMotorSpeed":
                    handleSetMotorSpeed(parts);
                    break;
                case "exit":
                    System.out.println("Exiting ...");
                    writeLogsToFiles();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
            }
            /*if (!command.equals("exit")) {
                System.out.println();
            }*/
        }
    }
    /**
     * Turns ON the device connected to the given port.
     *
     * @param parts Array of command parts: turnON <portID>
     */
    private void handleTurnON(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: turnON <portID>");
            return;
        }
    
        int portID;
        try {
            portID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid portID format.");
            return;
        }
    
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Error: Invalid portID.");
            return;
        }
    
        Protocol targetPort = ports.get(portID);
    
        // Sensor
        Iterator<Sensor> itS = sensors.iterator();
        while (itS.hasNext()) {
            Sensor s = itS.next();
            if (s != null && s.getProtocol() == targetPort) {
                s.turnON();
                return;
            }
        }
    
        // Display
        Iterator<Display> itD = displays.iterator();
        while (itD.hasNext()) {
            Display d = itD.next();
            if (d != null && d.getProtocol() == targetPort) {
                d.turnON();
                return;
            }
        }
    
        // WirelessIO
        Iterator<WirelessIO> itW = wirelessIOs.iterator();
        while (itW.hasNext()) {
            WirelessIO w = itW.next();
            if (w != null && w.getProtocol() == targetPort) {
                w.turnON();
                return;
            }
        }
    
        // MotorDriver
        Iterator<MotorDriver> itM = motorDrivers.iterator();
        while (itM.hasNext()) {
            MotorDriver m = itM.next();
            if (m != null && m.getProtocol() == targetPort) {
                m.turnON();
                return;
            }
        }
    
        System.err.println("Error: No device connected to this port.");
    }
    /**
    * Turns OFF the device connected to the given port.
    *
    * @param parts Array of command parts: turnOFF <portID>
    */
    private void handleTurnOFF(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: turnOFF <portID>");
            return;
        }
    
        int portID;
        try {
            portID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid portID format.");
            return;
        }
    
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Error: Invalid portID.");
            return;
        }
    
        Protocol targetPort = ports.get(portID);
    
        // Sensor
        Iterator<Sensor> itS = sensors.iterator();
        while (itS.hasNext()) {
            Sensor s = itS.next();
            if (s != null && s.getProtocol() == targetPort) {
                s.turnOFF();
                return;
            }
        }
    
        // Display
        Iterator<Display> itD = displays.iterator();
        while (itD.hasNext()) {
            Display d = itD.next();
            if (d != null && d.getProtocol() == targetPort) {
                d.turnOFF();
                return;
            }
        }
    
        // WirelessIO
        Iterator<WirelessIO> itW = wirelessIOs.iterator();
        while (itW.hasNext()) {
            WirelessIO w = itW.next();
            if (w != null && w.getProtocol() == targetPort) {
                w.turnOFF();
                return;
            }
        }
    
        // MotorDriver
        Iterator<MotorDriver> itM = motorDrivers.iterator();
        while (itM.hasNext()) {
            MotorDriver m = itM.next();
            if (m != null && m.getProtocol() == targetPort) {
                m.turnOFF();
                return;
            }
        }
        System.err.println("Error: No device connected to this port.");
    }
    /**
    * Lists either all ports or all devices of a specified type.
    *
    * @param parts Array of command parts: list ports OR list <DeviceType>
    */
    private void handleList(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: list ports OR list <DeviceType>");
            return;
        }
    
        if (parts[1].equalsIgnoreCase("ports")) {
            listPorts();
            return;
        }
    
        String type = parts[1].toLowerCase();
    
        if (type.equals("sensor")) {
            System.out.println("list of Sensors:");
            Iterator<Sensor> it = sensors.iterator();
            int devID = 0;
            while (it.hasNext()) {
                Sensor s = it.next();
                if (s != null) {
                    int portID = ports.indexOf(s.getProtocol());
                    String protocol = s.getProtocol().getProtocolName();
                    System.out.printf("%s %d %d %s\n", s.getName(), devID, portID, protocol);
                }
                devID++;
            }
    
        } else if (type.equals("display")) {
            System.out.println("list of Displays:");
            Iterator<Display> it = displays.iterator();
            int devID = 0;
            while (it.hasNext()) {
                Display d = it.next();
                if (d != null) {
                    int portID = ports.indexOf(d.getProtocol());
                    String protocol = d.getProtocol().getProtocolName();
                    System.out.printf("%s %d %d %s\n", d.getName(), devID, portID, protocol);
                }
                devID++;
            }
    
        } else if (type.equals("wirelessio")) {
            System.out.println("list of WirelessIOs:");
            Iterator<WirelessIO> it = wirelessIOs.iterator();
            int devID = 0;
            while (it.hasNext()) {
                WirelessIO w = it.next();
                if (w != null) {
                    int portID = ports.indexOf(w.getProtocol());
                    String protocol = w.getProtocol().getProtocolName();
                    System.out.printf("%s %d %d %s\n", w.getName(), devID, portID, protocol);
                }
                devID++;
            }
    
        } else if (type.equals("motordriver")) {
            System.out.println("list of Motor drivers:");
            Iterator<MotorDriver> it = motorDrivers.iterator();
            int devID = 0;
            while (it.hasNext()) {
                MotorDriver m = it.next();
                if (m != null) {
                    int portID = ports.indexOf(m.getProtocol());
                    String protocol = m.getProtocol().getProtocolName();
                    System.out.printf("%s %d %d %s\n", m.getName(), devID, portID, protocol);
                }
                devID++;
            }
    
        } else {
            System.err.println("Error: Unknown device type.");
            System.err.println("Valid types: Sensor, Display, WirelessIO, MotorDriver.");
        }
    }
    /**
    * Adds a device to a specified port with a given devID.
    * Verifies type compatibility, slot limits, and port availability.
    *
    * @param parts Array of command parts: addDev <devName> <portID> <devID>
    */
    private void handleAddDev(String[] parts) {
        if (parts.length != 4) {
            System.err.println("Error: Invalid arguments for addDev. Usage: addDev <devName> <portID> <devID>");
            return;
        }
    
        String devName = parts[1];
        int portID, devID;
    
        try {
            portID = Integer.parseInt(parts[2]);
            devID = Integer.parseInt(parts[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: portID and devID must be integers.");
            return;
        }
    
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Error: Invalid portID.");
            return;
        }
    
        Protocol selectedProtocol = ports.get(portID);
        Device newDevice = null;
    
        // Is port full
        if (portID < devices.size() && devices.get(portID) != null) {
            System.err.println("Error: Port " + portID + " is already occupied.");
            return;
        }
    
        switch (devName) {
            case "DHT11":
                if (!(selectedProtocol instanceof OneWire)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxSensors) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxSensors);
                    return;
                }
                newDevice = new DHT11(selectedProtocol);
                while (sensors.size() <= devID) sensors.add(null);
                if (sensors.get(devID) != null) {
                    System.err.println("Error: devID already in use for Sensor.");
                    return;
                }
                sensors.set(devID, (Sensor) newDevice);
                break;
    
            case "BME280":
                if (!(selectedProtocol instanceof I2C || selectedProtocol instanceof SPI)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxSensors) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxSensors);
                    return;
                }
                newDevice = new BME280(selectedProtocol);
                while (sensors.size() <= devID) sensors.add(null);
                if (sensors.get(devID) != null) {
                    System.err.println("Error: devID already in use for Sensor.");
                    return;
                }
                sensors.set(devID, (Sensor) newDevice);
                break;
    
            case "MPU6050":
                if (!(selectedProtocol instanceof I2C)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxSensors) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxSensors);
                    return;
                }
                newDevice = new MPU6050(selectedProtocol);
                while (sensors.size() <= devID) sensors.add(null);
                if (sensors.get(devID) != null) {
                    System.err.println("Error: devID already in use for Sensor.");
                    return;
                }
                sensors.set(devID, (Sensor) newDevice);
                break;
    
            case "GY951":
                if (!(selectedProtocol instanceof SPI || selectedProtocol instanceof UART)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxSensors) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxSensors);
                    return;
                }
                newDevice = new GY951(selectedProtocol);
                while (sensors.size() <= devID) sensors.add(null);
                if (sensors.get(devID) != null) {
                    System.err.println("Error: devID already in use for Sensor.");
                    return;
                }
                sensors.set(devID, (Sensor) newDevice);
                break;
    
            case "LCD":
                if (!(selectedProtocol instanceof I2C)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxDisplays) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxDisplays);
                    return;
                }
                newDevice = new LCD(selectedProtocol);
                while (displays.size() <= devID) displays.add(null);
                if (displays.get(devID) != null) {
                    System.err.println("Error: devID already in use for Display.");
                    return;
                }
                displays.set(devID, (Display) newDevice);
                break;
    
            case "OLED":
                if (!(selectedProtocol instanceof SPI)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxDisplays) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxDisplays);
                    return;
                }
                newDevice = new OLED(selectedProtocol);
                while (displays.size() <= devID) displays.add(null);
                if (displays.get(devID) != null) {
                    System.err.println("Error: devID already in use for Display.");
                    return;
                }
                displays.set(devID, (Display) newDevice);
                break;
    
            case "Bluetooth":
                if (!(selectedProtocol instanceof UART)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxWirelessIOs) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxWirelessIOs);
                    return;
                }
                newDevice = new Bluetooth(selectedProtocol);
                while (wirelessIOs.size() <= devID) wirelessIOs.add(null);
                if (wirelessIOs.get(devID) != null) {
                    System.err.println("Error: devID already in use for WirelessIO.");
                    return;
                }
                wirelessIOs.set(devID, (WirelessIO) newDevice);
                break;
    
            case "Wifi":
                if (!(selectedProtocol instanceof SPI || selectedProtocol instanceof UART)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxWirelessIOs) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxWirelessIOs);
                    return;
                }
                newDevice = new Wifi(selectedProtocol);
                while (wirelessIOs.size() <= devID) wirelessIOs.add(null);
                if (wirelessIOs.get(devID) != null) {
                    System.err.println("Error: devID already in use for WirelessIO.");
                    return;
                }
                wirelessIOs.set(devID, (WirelessIO) newDevice);
                break;
    
            case "PCA9685":
                if (!(selectedProtocol instanceof I2C)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxMotorDrivers) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxMotorDrivers);
                    return;
                }
                newDevice = new PCA9685(selectedProtocol);
                while (motorDrivers.size() <= devID) motorDrivers.add(null);
                if (motorDrivers.get(devID) != null) {
                    System.err.println("Error: devID already in use for MotorDriver.");
                    return;
                }
                motorDrivers.set(devID, (MotorDriver) newDevice);
                break;
    
            case "SparkFunMD":
                if (!(selectedProtocol instanceof SPI)) {
                    System.err.println("Error: Device and protocol mismatch.");
                    return;
                }
                if (devID >= maxMotorDrivers) {
                    System.err.printf("Error: All slots are full for %s. Maximum limit reached: %d\n", devName, maxMotorDrivers);
                    return;
                }
                newDevice = new SparkFunMD(selectedProtocol);
                while (motorDrivers.size() <= devID) motorDrivers.add(null);
                if (motorDrivers.get(devID) != null) {
                    System.err.println("Error: devID already in use for MotorDriver.");
                    return;
                }
                motorDrivers.set(devID, (MotorDriver) newDevice);
                break;
    
            default:
                System.err.println("Error: Unknown device type.");
                return;
        }
        while (devices.size() <= portID) devices.add(null);
        devices.set(portID, newDevice);

        /*System.out.printf("%s added successfully to port %d with devID %d\n", devName, portID, devID);*/
        System.out.printf("Device added.\n");
    }
    /**
    * Removes the device connected to the given port.
    * Device must be OFF to be removed.
    *
    * @param parts Array of command parts: rmDev <portID>
    */
    private void handleRmDev(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: rmDev <portID>");
            return;
        }
    
        int portID;
        try {
            portID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid portID.");
            return;
        }
    
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Error: Invalid portID.");
            return;
        }
    
        if (portID >= devices.size()) {
            System.err.println("Error: No device connected to this port.");
            return;
        }
    
        Device device = devices.get(portID);
        if (device == null) {
            System.err.println("Error: No device connected to this port.");
            return;
        }
    
        if (device.getState() == Device.State.ON) {
            System.err.println("Error: Device is ON. Turn it OFF before removal.");
            return;
        }
    
        // Sensor
        if (device instanceof Sensor) {
            Iterator<Sensor> it = sensors.iterator();
            int i = 0;
            while (it.hasNext()) {
                Sensor s = it.next();
                if (s == device) {
                    sensors.set(i, null);
                    break;
                }
                i++;
            }
            /*System.out.println("Sensor removed.");*/
            System.out.println("Device removed.");
        }
    
        // Display
        else if (device instanceof Display) {
            Iterator<Display> it = displays.iterator();
            int i = 0;
            while (it.hasNext()) {
                Display d = it.next();
                if (d == device) {
                    displays.set(i, null);
                    break;
                }
                i++;
            }
            /*System.out.println("Display removed.");*/
            System.out.println("Device removed.");
        }
    
        // WirelessIO
        else if (device instanceof WirelessIO) {
            Iterator<WirelessIO> it = wirelessIOs.iterator();
            int i = 0;
            while (it.hasNext()) {
                WirelessIO w = it.next();
                if (w == device) {
                    wirelessIOs.set(i, null);
                    break;
                }
                i++;
            }
            /*System.out.println("WirelessIO removed.");*/
            System.out.println("Device removed.");
        }
    
        // MotorDriver
        else if (device instanceof MotorDriver) {
            Iterator<MotorDriver> it = motorDrivers.iterator();
            int i = 0;
            while (it.hasNext()) {
                MotorDriver m = it.next();
                if (m == device) {
                    motorDrivers.set(i, null);
                    break;
                }
                i++;
            }
            /*System.out.println("MotorDriver removed.");*/
            System.out.println("Device removed.");
        }
    
        // Port'taki cihazı kaldır
        devices.set(portID, null);
    }
    /**
    * Reads and prints the data from a sensor with the given devID.
    *
    * @param parts Array of command parts: readSensor <devID>
    */
    private void handleReadSensor(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: readSensor <devID>");
            return;
        }
    
        int devID;
        try {
            devID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid devID.");
            return;
        }
    
        if (devID < 0 || devID >= sensors.size()) {
            System.err.println("Error: Invalid devID for Sensor.");
            return;
        }
    
        Sensor s = sensors.get(devID);
        if (s == null) {
            System.err.println("Error: No sensor found at this devID.");
            return;
        }
    
        if (s.getState() != Device.State.ON) {
            System.err.println("Error: Device is OFF. Turn it ON to read data.");
            return;
        }
    
        /*System.out.printf("%s %s: %s\n", s.getName(), s.getDevType(), s.data2String());*/
        /*System.out.println(s.data2String());*/
        System.out.println(s.readSensor());
    }
    /**
    * Sends a string to a display device with the given devID for printing.
    *
    * @param parts Array of command parts: printDisplay <devID> <String>
    */
    private void handlePrintDisplay(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: printDisplay <devID> <String>");
            return;
        }
    
        int devID;
        try {
            devID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid devID format.");
            return;
        }
    
        if (devID < 0 || devID >= displays.size()) {
            System.err.println("Error: Invalid devID for Display.");
            return;
        }
    
        Display display = displays.get(devID);
        if (display == null) {
            System.err.println("Error: No Display exists with this devID.");
            return;
        }
    
        if (display.getState() != Device.State.ON) {
            System.err.println("Error: Device is OFF. Turn it ON to print.");
            return;
        }
    
        // String catonate with StingBuilder AI assisted feature
        StringBuilder message = new StringBuilder();
        List<String> partsList = Arrays.asList(parts);
        Iterator<String> it = partsList.listIterator(2); // Start with second index
    
        while (it.hasNext()) {
            message.append(it.next());
            if (it.hasNext()) message.append(" ");
        }
        display.printData(message.toString());
    }
    /**
    * Receives the last message from a wireless IO device with the given devID.
    *
    * @param parts Array of command parts: readWireless <devID>
    */
    private void handleReadWireless(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Usage: readWireless <devID>");
            return;
        }
    
        int devID;
        try {
            devID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid devID.");
            return;
        }
    
        if (devID < 0 || devID >= wirelessIOs.size()) {
            System.err.println("Error: Invalid devID for WirelessIO.");
            return;
        }
    
        WirelessIO w = wirelessIOs.get(devID);
        if (w == null) {
            System.err.println("Error: No wireless adapter at this devID.");
            return;
        }
    
        if (w.getState() != Device.State.ON) {
            System.err.println("Error: Device is OFF. Turn it ON to read.");
            return;
        }
    
        String received = w.recvData();
        System.out.println(received);
    }
    /**
    * Sends a string via a wireless IO device with the given devID.
    *
    * @param parts Array of command parts: writeWireless <devID> <String>
    */
    private void handleWriteWireless(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: writeWireless <devID> <String>");
            return;
        }
    
        int devID;
        try {
            devID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid devID format.");
            return;
        }
    
        if (devID < 0 || devID >= wirelessIOs.size()) {
            System.err.println("Error: Invalid devID for WirelessIO.");
            return;
        }
    
        WirelessIO w = wirelessIOs.get(devID);
        if (w == null) {
            System.err.println("Error: No WirelessIO exists with this devID.");
            return;
        }
    
        if (w.getState() != Device.State.ON) {
            System.err.println("Error: Device is OFF. Turn it ON to write.");
            return;
        }
    
        // String catonate with iterator (AI assisted)
        StringBuilder message = new StringBuilder();
        List<String> partsList = Arrays.asList(parts);
        Iterator<String> it = partsList.listIterator(2); // index 2'den itibaren
    
        while (it.hasNext()) {
            message.append(it.next());
            if (it.hasNext()) message.append(" ");
        }
    
        w.sendData(message.toString());
    }
    /**
    * Sets the motor speed of a motor driver device with the given devID.
    *
    * @param parts Array of command parts: setMotorSpeed <devID> <speed>
    */
    private void handleSetMotorSpeed(String[] parts) {
        if (parts.length != 3) {
            System.out.println("Usage: setMotorSpeed <devID> <speed>");
            return;
        }
    
        int devID, speed;
    
        try {
            devID = Integer.parseInt(parts[1]);
            speed = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.err.println("Error: devID and speed must be integers.");
            return;
        }
    
        if (devID < 0 || devID >= motorDrivers.size()) {
            System.err.println("Error: Invalid devID for MotorDriver.");
            return;
        }
    
        MotorDriver m = motorDrivers.get(devID);
        if (m == null) {
            System.err.println("Error: No motor driver at this devID.");
            return;
        }
    
        if (m.getState() != Device.State.ON) {
            System.err.println("Error: Device is OFF. Turn it ON to set speed.");
            return;
        }
    
        m.setMotorSpeed(speed);
    }
    /**
    * Writes the protocol logs of each port to individual log files under the log directory.
    * The logs are written in reverse order (LIFO) starting from the most recent.
    */
    private void writeLogsToFiles() { /* AI assisted function getParentFile().mkdirs etc... */
        Iterator<Protocol> it = ports.iterator();
    
        while (it.hasNext()) {
            Protocol port = it.next();
            String fileName = logDirectory + "/" + port.getProtocolName() + "_" + port.getPortID() + ".log";
    
            try {
                File logFile = new File(fileName);
                logFile.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(logFile);
                Stack<String> logs = port.getLogs();
    
                while (true) {
                    try {
                        writer.write(logs.pop() + "\n");
                    } catch (Exception e) {
                        break; // If stack empty then exit
                    }
                }
                writer.close();
            } catch (IOException e) {
                System.err.println("Error writing log file for port: " + port.getProtocolName() + "_" + port.getPortID());
            }
        }
    }    
    /**
    * Lists all ports along with their status (occupied/empty), device details if occupied.
    */
    private void listPorts() {
        System.out.println("list of ports:");

        Iterator<Protocol> portIt = ports.iterator();
        int portIndex = 0;
        while (portIt.hasNext()) {
            Protocol protocol = portIt.next();
            String status = "empty";
            String devName = "";
            String devType = "";
            String state = "";
            int devID = -1;
    
            Device device = null;
            if (portIndex < devices.size()) {
                device = devices.get(portIndex);
            }
            if (device != null) {
                status = "occupied";
                devName = device.getName();
                devType = device.getDevType();
                state = (device.getState() == Device.State.ON) ? "ON" : "OFF";
                
                if (device instanceof Sensor) {
                    Iterator<Sensor> it = sensors.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        Sensor s = it.next();
                        if (s == device) {
                            devID = index;
                            break;
                        }
                        index++;
                    }
                } else if (device instanceof Display) {
                    Iterator<Display> it = displays.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        Display d = it.next();
                        if (d == device) {
                            devID = index;
                            break;
                        }
                        index++;
                    }
                } else if (device instanceof WirelessIO) {
                    Iterator<WirelessIO> it = wirelessIOs.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        WirelessIO w = it.next();
                        if (w == device) {
                            devID = index;
                            break;
                        }
                        index++;
                    }
                } else if (device instanceof MotorDriver) {
                    Iterator<MotorDriver> it = motorDrivers.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        MotorDriver m = it.next();
                        if (m == device) {
                            devID = index;
                            break;
                        }
                        index++;
                    }
                }
            }
            if (status.equals("empty")) {
                System.out.printf("%d %s empty\n", portIndex, protocol.getProtocolName());
            } else {
                System.out.printf("%d %s %s %s %s %d %s\n",
                        portIndex, protocol.getProtocolName(), status, devName, devType, devID, state);
            }
            portIndex++;
        }
        /*System.out.println();*/
    }    
}