package studio.spiderling.Commandler;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.core.event.message.MessageCreateEventImpl;

public class CommandContext extends MessageCreateEventImpl {
    private final String command;
    private final String prefix;
    private final String[] args;

    public CommandContext(MessageCreateEvent event, String commandUsed, String prefixUsed, String[] argsGiven) {
        super(event.getMessage());
        command = commandUsed;
        prefix = prefixUsed;
        args = argsGiven;
    }

    public String getCommand() {
        return command;
    }

    public String getPrefix() {
        return prefix;
    }

    public String[] getArgs() {
        return args;
    }
}
