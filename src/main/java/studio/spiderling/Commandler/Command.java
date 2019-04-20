package studio.spiderling.Commandler;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public abstract class Command implements MessageCreateListener {
	// TODO: class MessageCreateEvent object to simplify methods and responses
	private MessageCreateEvent event;

    public abstract void onCommand(MessageCreateEvent event, String[] args);
    public abstract List<String> Aliases();
    public abstract String Description();
    public abstract String Name();
    public abstract String Usage();
    public abstract String Category();
    public abstract List<String> Permissions();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
    	this.event = event;
        if (!isValidSource()) {
            // TODO: Move to isValidSource() to weed out bot users, and blacklisted servers/users (soon tm).
            // Ignore bot users
            return;
        }
        if (!this.event.getMessageContent().substring(0, grabPrefix(this.event.getServer().get().getIdAsString()).length()).equals(grabPrefix(this.event.getServer().get().getIdAsString()))) {
            // TODO: Move to separate method 'startsWithTrigger()' to detect prefixes and bot mentions.
            // Ignore prefixes that aren't in the config or the database
            return;
        }
        if (!isCommand()) {
            // Ignore any message that doesn't start with a registered command or its alias
            return;
        }
        if (!hasPermission()) {
            return;
        }

        this.event.getApi().getThreadPool().getExecutorService().submit(() -> onCommand(this.event, getCommandArgs()));
    }

    private String[] cutPrefix() {
        // Remove the prefix from the command
        return event.getMessageContent().substring(grabPrefix(this.event.getServer().get().getIdAsString()).length()).split(" ");
    }

    private boolean isValidSource() {
        // Check if the validity of the source
        if (event.getMessageAuthor().isWebhook()) {
            // Ignore any messages from webhooks
            return false;
        }
        else if (event.getMessageAuthor().asUser().orElseThrow(AssertionError::new).isBot()) {
            // Ignore bot users
            return false;
        }
        else {
            // Fallback to allowing all unmatched sources
            return true;
        }

        // TODO: framework-managed blacklist for users and servers. If user -> ignore. If server -> leave.
    }

    private boolean startsWithTrigger() {
        //! Need to accommodate for cutPrefix(). Remove the bot mention from the command args instead of the prefix. Maybe a separate mention module?
        if (event.getMessageContent().substring(0, grabPrefix(event.getServer().get().getIdAsString()).length()).equals(grabPrefix(event.getServer().get().getIdAsString()))) {
            // Starting with the prefix is a valid trigger. Accounts for prefix length.
            return true;
        }

        if (event.getMessage().getMentionedUsers().contains(event.getApi().getYourself())) {
            // Containing a bot mention is a sufficient trigger; anywhere in the command.
            return true;
        }

        // Fallback to ignoring messages if no cases are matched.
        return false;
    }

    private boolean isCommand() {
        // Check if the string is a command or a command alias
        return Aliases().contains(this.cutPrefix()[0].toLowerCase());
    }

    private String[] getCommandArgs() {
        // Get the arguments; remove the command itself
        return Arrays.copyOfRange(this.cutPrefix(), 1, this.cutPrefix().length);
    }

    private boolean hasPermission() {
        // Check if the user has permission to use the command.
        // Checks permission based on this.Permissions()

        List<String> authorPermissions = new ArrayList<>();

        this.event.getServer().ifPresent(server -> {
            this.event.getMessageAuthor().asUser().ifPresent(user -> {
                server.getAllowedPermissions(user).forEach(perm -> {
                    authorPermissions.add(perm.name());
                });
            });
        });

        if (this.Permissions().contains("none")) {
            // If none even exists, allow command usage
            // none will override any other permission entered
            return true;
        }

        if (this.Permissions().contains("BOT_OWNER") && event.getMessageAuthor().isBotOwner()) {
            // If BOT_OWNER even exists, restrict to bot owner
            return true;
        }

        if (authorPermissions.containsAll(this.Permissions())) {
            // If the message author has all the reqperms, allow usage.
            // All permissions in reqperms are required for command usage to be allowed.
            return true;
        }

        // Fallback to disallowing command usage if no cases are matched
        event.getChannel().sendMessage(PrefabResponses.noPermissions(event, this.Permissions()));
        return false;
    }

    private static String grabPrefix(String serverId) {
        if (FrameworkDB.getServerPrefix(serverId) == null) {
            return FrameworkConfig.getDefaultPrefix();
        }
        else {
            return FrameworkDB.getServerPrefix(serverId);
        }
    }

    protected Future<Message> sendResponse(String message) {
        return event.getChannel().sendMessage(message);
    }

    protected Future<Message> sendResponse(EmbedBuilder embed) {
        return event.getChannel().sendMessage(embed);
    }

    protected Future<Message> sendResponse(File file) {
        return event.getChannel().sendMessage(file);
    }

    protected Future<Message> sendResponse(File file, String message) {
        return event.getChannel().sendMessage(message, file);
    }
}
