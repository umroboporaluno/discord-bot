package br.natalnet.listener;

import br.natalnet.BotApplication;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class DoubtsEmbedMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getGuild().getIdLong() != BotApplication.getApplication().getDiscordId())
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
                        + "☎️ > Dúvidas sobre o URA;\n"
                        + "\uD83D\uDDA5 > Dúvidas sobre o hardware;\n"
                        + "\uD83E\uDDD1\u200D\uD83D\uDCBB > Dúvidas sobre o software;\n"
                        + "⏰ > Agende um horário;").build())
                .setActionRow(Button.primary("doubts-ura", "Dúvidas sobre o URA"),
                        Button.primary("doubts-hardware", "Dúvidas sobre o hardware"),
                        Button.primary("doubts-software", "Dúvidas sobre o software"),
                        Button.primary("doubts-horario", "Agende um horário"))
                .queue();
    }
}
