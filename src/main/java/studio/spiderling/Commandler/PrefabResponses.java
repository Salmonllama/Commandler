package studio.spiderling.Commandler;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

class PrefabResponses {

    static EmbedBuilder noPermissions(MessageCreateEvent event, String permission) {
        return new EmbedBuilder()
            .setColor(Color.RED)
            .setAuthor(event.getApi().getYourself())
            .addField("Missing Permissions:", permission);
    }

    static EmbedBuilder improperUsage(MessageCreateEvent event, String usage) {
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
