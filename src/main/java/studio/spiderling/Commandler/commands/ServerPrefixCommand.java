package studio.spiderling.Commandler.commands;

import studio.spiderling.Commandler.Command;
import studio.spiderling.Commandler.FrameworkDB;
import studio.spiderling.Commandler.PrefabResponses;

import java.util.List;
import java.util.Arrays;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

public class ServerPrefixCommand extends Command {
    @Override
    public String Name() { return "Server Prefix Command"; }
    @Override
    public String Description() { return "View or change the server-specific prefix"; }
    @Override
    public String Category() { return "Commandler"; }
    @Override
    public String Usage() { return "prefix [String newPrefix]"; }
    @Override
    public List<String> Aliases() { return Arrays.asList("prefix", "serverprefix", "sp", "setprefix", "changeprefix"); }
    @Override
    public List<String> Permissions() { return Arrays.asList("MANAGE_SERVER"); }

    @Override
    public void onCommand(MessageCreateEvent event, String[] args) {
        if (event.isPrivateMessage()) {
            sendResponse("This command is for managing the *server* prefix. Please use this again in a server.");
            return;
        }

        if (args.length == 0) {
            EmbedBuilder embed = new EmbedBuilder();

            event.getServer().ifPresent(server -> {
                embed.setTitle("Current Prefix")
                    .setDescription("```\n" + FrameworkDB.getServerPrefix(server.getIdAsString()) + "\n```");
            });

            sendResponse(embed);
            return;
        }
        else if (args.length == 1) {
            EmbedBuilder embed = new EmbedBuilder();

            event.getServer().ifPresent(server -> {
                FrameworkDB.setServerPrefix(server.getIdAsString(), args[1]);

                embed.setTitle("Prefix Changed")
                    .setDescription("**New Prefix:\n" + "```\n" + args[1] + "\n```");
            });

            sendResponse(embed);
            return;
        }
        else {
            sendResponse(PrefabResponses.improperUsage(event, this.Usage()));
        }
    }
}
