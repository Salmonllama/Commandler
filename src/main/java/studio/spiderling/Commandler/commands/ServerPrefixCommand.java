package studio.spiderling.Commandler.commands;

import studio.spiderling.Commandler.Command;
import java.util.List;
import java.util.Arrays;
import org.javacord.api.event.message.MessageCreateEvent;

public class ServerPrefixCommand extends Command {
    @Override
    public String Name() { return "Server Prefix Command"; }
    @Override
    public List<String> Aliases() { return Arrays.asList("prefix", "serverprefix", "sp"); }
    @Override
    public String Description() { return "View or change the server prefix"; }
    @Override
    public String Usage() { return "example <arguments>"; }
    @Override
    public List<String> Permissions() { return Arrays.asList("none"); }
    @Override
    public String Category() { return "Example Commands"; }

    @Override
    public void onCommand(MessageCreateEvent event, String[] args) {
        String response;

        if (args.length > 0) {
            response = String.join(" ", args);

            sendResponse(response);
            return;
        }

        response = "Welcome to the Commandler framework!";
        sendResponse(response);
        return;
    }
}
