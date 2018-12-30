package org.crabcraft.Commandler.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.crabcraft.Commandler.Command;
import org.crabcraft.Commandler.FrameworkConfig;
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
    		sendResponse(event, defaultPrefixGet());
    		return;
    	}
    	else if (args.length == 1) {
    		// Set the dprefix
    	}
    }
    
    EmbedBuilder defaultPrefixGet() {
    	return new EmbedBuilder()
				.setTitle("Current Prefix")
				.setColor(Color.GREEN)
				.setDescription(String.format("```%s```", FrameworkConfig.getDefaultPrefix()));
    }
    
    EmbedBuilder defaultPrefixSet(String newPrefix) {
    	FrameworkConfig.setDefaultPrefix(newPrefix);
    	
    	return new EmbedBuilder()
    			.setTitle("Prefix Updated")
    			.setColor(Color.GREEN)
    			.addField("New Prefix:", String.format("```%s```", newPrefix));
    }
}
