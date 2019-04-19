package studio.spiderling.Commandler;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import studio.spiderling.Commandler.commands.DefaultPrefixCommand;
import org.javacord.api.DiscordApi;

/*
* The base registry.
* Used to register commands and track registered commands.
*/
public class CommandRegistry {
    private static TreeMap<String, Command> commandsMap;
    private static List<Command> commandsList;

    public CommandRegistry() {
        commandsMap = new TreeMap<>();
        commandsList = new ArrayList<Command>();

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
        commandsMap.put(command.Aliases().get(0), command);
        commandsList.add(command);
        return command;
    }

    /**
     * Gets the TreeMap of registered commands.
     * 
     * @return The TreeMap of commands
     */
    public TreeMap<String, Command> getCommandsMap() {
        return commandsMap;
    }

    /**
     * Gets the List of registered commands.
     * 
     * @return The List<Command> of commands.
     */
    public List<Command> getCommandsList() {
        return commandsList;
    }
    
    /**
     * Optional step. Adds the pre-built commands and listeners. Required for server-specific prefixes
     * 
     * @param api The global DiscordApi instance
     */
    public void addCommandlerWorkloads(DiscordApi api) {
    	// TODO: HelpCommand, ServerPrefix commands, DefaultPrefix commands.
        api.addMessageCreateListener(this.registerCommand(new DefaultPrefixCommand())); //! Remove. Default prefix only editable through config.
        api.addServerJoinListener(new FrameworkServerInit());
    }

    /**
     * Gets the prefix of the server, for use mostly in help or utility commands.
     * 
     * @param serverId The serverId of the server of origin.
     * @return The prefix of the server, or the default prefix if none set.
     */
    public String getUsedPrefix(String serverId) {
        if (FrameworkDB.getServerPrefix(serverId) == null) {
            return FrameworkConfig.getDefaultPrefix();
        }
        else {
            return FrameworkDB.getServerPrefix(serverId);
        }
    }
}