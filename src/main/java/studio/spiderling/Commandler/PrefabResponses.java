package studio.spiderling.Commandler;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.List;

public class PrefabResponses {

    public static EmbedBuilder noPermissions(MessageCreateEvent event, List<String> permissions) {
        StringBuilder builder = new StringBuilder()
                .append("```md\n");

        permissions.forEach(permission -> builder.append(String.format("- %s", permission)).append("\n"));
        builder.append("```");

        return new EmbedBuilder()
            .setColor(Color.RED)
            .setAuthor(event.getApi().getYourself())
            .addField("Required Permissions:", builder.toString());
    }

    public static EmbedBuilder improperUsage(MessageCreateEvent event, String usage) {
        // ?Would the command description would be viable in this embed.
        return new EmbedBuilder()
            .setColor(Color.YELLOW)
            .setAuthor(event.getApi().getYourself())
            .setTitle("Improper Command Usage")
            .addField("Usage:", usage);
    }

    /*
     * TODO: Add bundled prefab responses for:
     *  - Improper command usage, includes proper command usage + description // DONE
     *  - Errors, or warnings, with custom fields for displaying an error message
     */

}
