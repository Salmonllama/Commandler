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

    public abstract void onCommand(MessageCreateEvent event, String[] args);
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
        // TODO: make sure it tracks the prefix length too, in cutPrefix() as well.
        if (!event.getMessageContent().split("")[0].equals(grabPrefix(event.getServer().get().getIdAsString()))) {
            // Ignore prefixes that aren't in the config
            return;
        }
        if (!isCommand(event.getMessageContent())) {
            // Ignore any message that doesn't start with a registered command or its alias
            return;
        }
        if (!hasPermission(event, event.getMessageAuthor().asUser().orElseThrow(AssertionError::new))) {
            return;
        }

        onCommand(event, getCommandArgs(event.getMessageContent()));
    }

    private static String[] cutPrefix(String message) {
        // Remove the prefix from the command
        return message.substring(1).split(" ");
    }

    private boolean isCommand(String string) {
        // Check if the string is a command or a command alias
        return Aliases().contains(Command.cutPrefix(string)[0]);
    }

    private String[] getCommandArgs(String message) {
        // Get the arguments; remove the command itself
        return Arrays.copyOfRange(cutPrefix(message), 1, cutPrefix(message).length);
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
        
        if (!event.getServer().get().getPermissions(author).getAllowedPermission().toString().contains(this.Permission())) {
            // If the user doesn't have reqperm, don't let them use the command!
            event.getChannel().sendMessage(PrefabResponses.noPermissions(event, this.Permission()));
            return false;
        }

        return true;
    }

    private String grabPrefix(String serverId) {
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