package org.crabcraft.Commandler;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public abstract class Command implements MessageCreateListener {

    public abstract void onCommand(MessageCreateEvent mcEvent, String[] args);
    public abstract List<String> Aliases();
    public abstract String Description();
    public abstract String Name();
    public abstract String Usage();
    public abstract String Category();
    public abstract String Permission();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageAuthor().asUser().map(User::isBot).orElse(true)) {
            // Ignore bot users
            return;
        }
        if (!event.getMessageContent().substring(0, grabPrefix(event.getServer().get().getIdAsString()).length()).equals(grabPrefix(event.getServer().get().getIdAsString()))) {
            // Ignore prefixes that aren't in the config or the database
            return;
        }
        if (!isCommand(event)) {
            // Ignore any message that doesn't start with a registered command or its alias
            return;
        }
        if (!hasPermission(event, event.getMessageAuthor().asUser().orElseThrow(AssertionError::new))) {
            return;
        }

        onCommand(event, getCommandArgs(event));
    }

    private static String[] cutPrefix(MessageCreateEvent event) {
        // Remove the prefix from the command
        return event.getMessageContent().toLowerCase().substring(grabPrefix(event.getServer().get().getIdAsString()).length()).split(" ");
    }

    private boolean isCommand(MessageCreateEvent event) {
        // Check if the string is a command or a command alias
        return Aliases().contains(Command.cutPrefix(event)[0]);
    }

    private String[] getCommandArgs(MessageCreateEvent event) {
        // Get the arguments; remove the command itself
        return Arrays.copyOfRange(cutPrefix(event), 1, cutPrefix(event).length);
    }

    private boolean hasPermission(MessageCreateEvent event, User author) {
        // Check if the user has permission to use that command
        if (this.Permission().equals("none")) {
            // Allow everyone to use a command with no reqperms
            return true;
        }

        if (this.Permission().equals("BOT_OWNER")) {
            // Only allow the bot owner to use command with BOT_OWNER reqperms
            if (!event.getMessageAuthor().isBotOwner()) {
                event.getChannel().sendMessage(PrefabResponses.noPermissions(event, this.Permission()));
                return false;
            }
        }
        
        if (!event.getServer().get().getPermissions(event.getMessageAuthor().asUser().orElseThrow(AssertionError::new)).getAllowedPermission().toString().contains(this.Permission())) {
            // If the user doesn't have reqperm, don't let them use the command!
            event.getChannel().sendMessage(PrefabResponses.noPermissions(event, this.Permission()));
            return false;
        }

        return true;
    }

    private static String grabPrefix(String serverId) {
        if (FrameworkDB.getServerPrefix(serverId) == null) {
            return FrameworkConfig.getDefaultPrefix();
        }
        else {
            return FrameworkDB.getServerPrefix(serverId);
        }
    }

    protected Future<Message> sendResponse(MessageCreateEvent event, String message) {
        return event.getChannel().sendMessage(message);
    }

    protected Future<Message> sendResponse(MessageCreateEvent event, EmbedBuilder embed) {
        return event.getChannel().sendMessage(embed);
    }

    protected Future<Message> sendResponse(MessageCreateEvent event, File file) {
        return event.getChannel().sendMessage(file);
    }

    protected Future<Message> sendResponse(MessageCreateEvent event, File file, String message) {
        return event.getChannel().sendMessage(message, file);
    }
}