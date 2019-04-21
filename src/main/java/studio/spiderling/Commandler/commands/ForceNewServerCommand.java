package studio.spiderling.Commandler.commands;

import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;

import studio.spiderling.Commandler.Command;
import studio.spiderling.Commandler.FrameworkConfig;
import studio.spiderling.Commandler.FrameworkDB;

public class ForceNewServerCommand extends Command {
    @Override
    public String Name() { return "Force New Server Command"; }
    @Override
    public String Description() { return "Forces the new server process. Useful in cases where you transition to Commandler and don't want to have your bot leave/rejoin."; }
    @Override
    public String Category() { return "Commandler"; }
    @Override
    public String Usage() { return "forcenewserver"; }
    @Override
    public List<String> Aliases() { return Arrays.asList("forcenewserver", "forceserverinit"); }
    @Override
    public List<String> Permissions() { return Arrays.asList("ADMINISTRATOR"); }

    @Override
    public void onCommand(MessageCreateEvent event, String[] args) {
        event.getServer().ifPresent(server -> {
            String serverId = server.getIdAsString();
            String prefix = FrameworkConfig.getDefaultPrefix();

            FrameworkDB.serverFirstTimeSetup(prefix, serverId);
        });

        sendResponse("Done!");
    }
}