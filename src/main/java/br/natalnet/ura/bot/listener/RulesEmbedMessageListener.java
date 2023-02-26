package br.natalnet.ura.bot.listener;

import br.natalnet.ura.bot.BotApplication;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class RulesEmbedMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getGuild().getIdLong() != BotApplication.getApplication().getDiscordId())
            return;

        if (event.getAuthor().isBot() || event.isWebhookMessage())
            return;

        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("!regras"))
            return;

        if (event.getMember() == null)
            return;

        if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
            return;

        String rules = "1. Você deve ter vínculo ativo com o projeto do URA \n2. <rule-2> \n3. <rule-3>";

        event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                        .setAuthor("Regras")
                        .setDescription(rules)
                        .setFooter("Esteja ciente que qualquer descumprimento das regras acima resulta em punição.")
                        .setColor(Color.BLUE)
                        .build())
                .queue();
    }
}
