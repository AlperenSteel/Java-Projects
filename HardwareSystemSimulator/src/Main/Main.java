package Main;

import hwSystem.hwSystem;
/**
* Main class for running the hardware system simulation.
* Loads configuration and executes all commands.
*/
public class Main {
    /**
    * Default constructor for the Main class.
    * This class should only be used statically via the main method.
    */
    public Main() {
        // Not used
    }
    /**
    * The main method of the program.
    * Accepts two command-line arguments: configuration file path and log output directory.
    * Loads the configuration and executes commands from the input scenario.
    *
    * @param args Command-line arguments: [0] = config file path, [1] = log directory
    */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main <configFilePath> <logDirectoryPath>");
            System.exit(1);
        }

        String configPath = args[0];
        String logDirPath = args[1];

        hwSystem system = new hwSystem(logDirPath);
        system.loadConfiguration(configPath);
        system.run();           // run the system
        system.runCommands();   // after exit command, run all commands in the queue
    }
}
