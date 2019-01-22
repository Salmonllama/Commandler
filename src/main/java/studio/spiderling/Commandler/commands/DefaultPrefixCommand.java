package studio.spiderling.Commandler.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import studio.spiderling.Commandler.Command;
import studio.spiderling.Commandler.FrameworkConfig;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class DefaultPrefixCommand extends Command {
    @Override
    public String Name() { return "Default Prefix Command"; }
    @Override
    public List<String> Aliases() { return Arrays.asList("defaultprefix", "dprefix", "dp"); }
    @Override
    public String Description() { return "View or edit the default framework prefix"; }
    @Override
    public String Usage() { return "defaultprefix [set:newprefix]"; }
    @Override
    public String Permission() { return "BOT_OWNER"; }
    @Override
    public String Category() { return "Framework"; }
    
    @Override
    public void onCommand(MessageCreateEvent event, String[] args) {
    	if (args.length == 0) {
    		// Get the dprefix
    		sendResponse(defaultPrefixGet());
    		return;
    	}
    	else if (args.length == 1) {
    		// Set the dprefix
    		sendResponse(defaultPrefixSet(args[0].split(":")[1]));
    	}
    }
    
    EmbedBuilder defaultPrefixGet() {
    	return new EmbedBuilder()
				.setTitle("Current Default Prefix")
				.setColor(Color.GREEN)
				.setDescription(String.format("```%s```", FrameworkConfig.getDefaultPrefix()));
    }
    
    EmbedBuilder defaultPrefixSet(String newPrefix) {
    	FrameworkConfig.setDefaultPrefix(newPrefix);
    	
    	return new EmbedBuilder()
    			.setTitle("Default Prefix Updated")
    			.setColor(Color.GREEN)
    			.addField("New Default Prefix:", String.format("```%s```", newPrefix));
    }
}
