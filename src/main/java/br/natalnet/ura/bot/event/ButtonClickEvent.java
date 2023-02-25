package br.natalnet.ura.bot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.*;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class ButtonClickEvent extends ListenerAdapter {

    /*
    Voluntário role ID -> 1078748863705383023
    Hardware leader role ID -> 1078759257098432603
    Bolsista role ID -> 1078852871807836261
     */

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (!event.isFromGuild())
            return;

        if (event.getButton().getId() == null)
            return;

        if (event.getMember() == null)
            return;

        if (event.getGuild() == null)
            return;

        Guild guild = event.getGuild();

        final boolean[] cancel = { false };

        if (event.getButton().getId().startsWith("doubts-")) {

            guild.getTextChannels().forEach(textChannel -> {

                if (textChannel.getTopic() != null) {

                    if (textChannel.getTopic().contains(event.getMember().getId())) {
                        event.reply("Você já possui uma dúvida/solicitação em aberto, utilize o canal " + textChannel.getAsMention() + "!")
                                .setEphemeral(true).queue();

                        cancel[0] = true;
                    }
                }
            });
        }

        if (cancel[0])
            return;

        if (event.getButton().getId().equalsIgnoreCase("doubts-ura")) {

            Category category = event.getJDA().getCategoryById(1078900379279962232L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-ura: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1078748863705383023L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre o URA**, aguarde dentro de alguns"
                                        + " instantes que algum voluntário irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o URA foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@1078748863705383023>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase("doubts-hardware")) {

            Category category = event.getJDA().getCategoryById(1078900379279962232L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-hardware: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1078759257098432603L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre o hardware**, aguarde dentro de alguns"
                                        + " instantes que algum membro da área de hardware irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o hardware foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@1078759257098432603>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase("doubts-software")) {

            Category category = event.getJDA().getCategoryById(1078900379279962232L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-hardware: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1078760557202657451L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre o software**, aguarde dentro de alguns"
                                        + " instantes que algum membro da área de software irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o software foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@1078760557202657451>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase( "doubts-horario")) {

            Category category = event.getJDA().getCategoryById(1078900379279962232L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("agendamento: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1078852871807836261L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **agendamento de horário**, aguarde dentro de alguns"
                                        + " instantes que algum bolsista do LAR (Laborátório de Automação e Robótica) irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o software foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@1078852871807836261>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase("close-doubt")) {

            if (!event.getMember().getPermissions().contains(Permission.KICK_MEMBERS)) {
                event.reply("Você não tem permissão de fechar o seu ticket.").setEphemeral(true).queue();
                return;
            }

            event.reply("Você encerrou o ticket!").setEphemeral(true).queue();

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            TextChannel textChannel = (TextChannel) event.getChannel();

            String id = textChannel.getTopic();

            if (id == null)
                return;

            guild.retrieveMemberById(id).queue(member -> textChannel.upsertPermissionOverride(member).setDenied(permissions).queue());

            textChannel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle("URA • Ticket")
                    .setColor(Color.BLUE)
                    .setAuthor("Menu de ações:")
                    .build())
                    .setActionRow(StringSelectMenu.create("menu:ticket")
                            .setPlaceholder("Clique para ver as opções")
                            .setRequiredRange(1, 1)
                            .addOption("Reabrir o ticket", "menu-reopen")
                            .addOption("Deletar o ticket", "menu-delete")
                            .build())
                    .queue();
        }
    }
}
