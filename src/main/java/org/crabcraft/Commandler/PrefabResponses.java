package org.crabcraft.Commandler;

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

    /*
     * TODO: Add bundled prefab responses for:
     *  - Improper command usage, includes proper command usage + description
     *  - Errors, or warnings, with custom fields for displaying an error message
     */

}
