package org.crabcraft.Commandler;

import java.util.TreeMap;

    /*
    * The base registry.
    * Used to register commands and track registered commands.
    */
public class CommandRegistry {
    private static TreeMap<String, Command> commands;

    public CommandRegistry() {
        commands = new TreeMap<>();

        // Run Config and DB setups
        FrameworkConfig.firstTimeSetup();
        FrameworkDB.firstTimeSetup();
    }

    /**
    * Registers the command, adding it to the global registry.
    * Returns the command, making it easy to add a messageCreateListener through Javacord and register the command.
    *
    * @param command The command to be registered
    * @return The registered command
    */
    public Command registerCommand(Command command) {
        commands.put(command.Aliases().get(0), command);
        return command;
    }

    /**
     * Gets the list of registered commands.
     * 
     * @return The TreeMap of commands
     */
    public TreeMap<String, Command> getCommands() {
        return commands;
    }
}