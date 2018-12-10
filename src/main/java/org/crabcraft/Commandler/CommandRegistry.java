package org.crabcraft.Commandler;

import org.javacord.api.DiscordApi;

import java.util.TreeMap;

public class CommandRegistry {
    private DiscordApi api;
    private static TreeMap<String, Command> commands;

    public CommandRegistry(DiscordApi dApi) {
        commands = new TreeMap<>();

        // Run Config and DB setups
        FrameworkConfig.firstTimeSetup();
        FrameworkDB.firstTimeSetup();

        try {
            this.api = dApi;
            this.api.addServerJoinListener(event -> {
                FrameworkDB.serverFirstTimeSetup(event.getServer().getName(), event.getServer().getIdAsString());
            });
        }
        catch (NullPointerException e) {
            // ignore
        }
    }

    public Command registerCommand(Command command) {
        commands.put(command.Aliases().get(0), command);
        return command;
    }

    public TreeMap<String, Command> getCommands() {
        return commands;
    }
}