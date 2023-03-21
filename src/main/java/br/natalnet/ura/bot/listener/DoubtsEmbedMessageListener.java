package br.natalnet.ura.bot.listener;

import br.natalnet.ura.bot.BotApplication;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class DoubtsEmbedMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getGuild().getIdLong() != BotApplication.getSystem().getDiscordId())
            return;

        if (event.getAuthor().isBot() || event.isWebhookMessage())
            return;

        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("!doubts"))
            return;

        if (event.getMember() == null)
            return;

        if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
            return;

        event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                .setDescription("Olá, seja bem-vindo ao atendimento do URA. Abaixo, há diversas "
                        + "categorias para você decidir em qual das aŕeas sua dúvida/solicitação se encaixa.\n\n"
                        + "**Categorias:**\n\n"
                        + "\uD83E\uDD16 > Dúvidas sobre o curso;\n"
                        + "⚙️ > Dúvidas sobre as engenharias;\n"
                        + "\uD83D\uDE80 > Dúvidas sobre o marketing;\n"
                        + "⏰ > Agende um horário;")
                .setColor(Color.BLUE).build())
                .setActionRow(Button.primary("doubts-course", "Dúvidas sobre o curso"),
                        Button.primary("doubts-engineer", "Dúvidas sobre as engenharias"),
                        Button.primary("doubts-marketing", "Dúvidas sobre o marketing"),
                        Button.primary("doubts-horario", "Agende um horário"))
                .queue();
    }
}
