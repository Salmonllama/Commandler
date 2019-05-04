package studio.spiderling.Commandler.commands;

import studio.spiderling.Commandler.Command;
import studio.spiderling.Commandler.CommandContext;
import studio.spiderling.Commandler.FrameworkDB;
import studio.spiderling.Commandler.PrefabResponses;

import java.util.List;
import java.util.Arrays;

import org.javacord.api.entity.message.embed.EmbedBuilder;

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
    public void onCommand(CommandContext ctx) {
        if (ctx.isPrivateMessage()) {
            sendResponse("This command is for managing the *server* prefix. Please use this again in a server.");
            return;
        }

        if (ctx.getArgs().length == 0) {
            EmbedBuilder embed = new EmbedBuilder();

            ctx.getServer().ifPresent(server -> {
                embed.setTitle("Current Prefix")
                    .setDescription("```\n" + FrameworkDB.getServerPrefix(server.getIdAsString()) + "\n```");
            });

            sendResponse(embed);
            return;
        }
        else if (ctx.getArgs().length == 1) {
            EmbedBuilder embed = new EmbedBuilder();

            ctx.getServer().ifPresent(server -> {
                FrameworkDB.setServerPrefix(server.getIdAsString(), ctx.getArgs()[1]);

                embed.setTitle("Prefix Changed")
                    .setDescription("**New Prefix:\n" + "```\n" + ctx.getArgs()[1] + "\n```");
            });

            sendResponse(embed);
            return;
        }
        else {
            sendResponse(PrefabResponses.improperUsage(ctx, this.Usage()));
        }
    }
}
