package org.crabcraft.Commandler;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

class PrefabResponses {

    static EmbedBuilder noPermissions(MessageCreateEvent event, String permission) {

        EmbedBuilder embed = new EmbedBuilder()
            .setColor(Color.RED)
            .setAuthor(event.getApi().getYourself())
            .addField("Missing Permissions:", permission);

        return embed;
    }
}
