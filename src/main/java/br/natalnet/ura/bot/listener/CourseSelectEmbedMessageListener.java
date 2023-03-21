package br.natalnet.ura.bot.listener;

import br.natalnet.ura.bot.BotApplication;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;


public class CourseSelectEmbedMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getGuild().getIdLong() != BotApplication.getSystem().getDiscordId())
            return;

        if (event.getAuthor().isBot() || event.isWebhookMessage())
            return;

        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("!courses"))
            return;

        if (event.getMember() == null)
            return;

        if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
            return;

        String roles = "Escolha entre algum dos cursos disponíveis abaixo, após a seleção você terá o cargo do respectivo curso.";

        event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                .setAuthor("Seleção do curso")
                .setDescription(roles)
                .setColor(Color.BLUE)
                .build()).setActionRow(Button.primary("course-ura-def", "Curso graduandos"), Button.primary("course-ura-teacher", "Curso professores"), Button.primary("course-ura-em", "Curso ensino médio"))
                .queue();
    }
}
