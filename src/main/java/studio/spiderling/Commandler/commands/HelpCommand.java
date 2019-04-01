package studio.spiderling.Commandler.commands;

import java.util.List;
import java.util.Arrays;

import studio.spiderling.Commandler.Command;
import org.javacord.api.event.message.MessageCreateEvent;

public class HelpCommand extends Command {
    @Override
    public String Name() { return "Help Command"; }
    @Override
    public List<String> Aliases() { return Arrays.asList("help", "h"); }
    @Override
    public String Description() { return "Shows command info, or a list of commands."; }
    @Override
    public String Usage() { return "help [command]"; }
    @Override
    public List<String> Permissions() { return Arrays.asList("none"); }
    @Override
    public String Category() { return "Framework"; }
    
    @Override
    public void onCommand(MessageCreateEvent event, String[] args) {
    	
    }

}
