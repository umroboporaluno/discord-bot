package br.natalnet.ura.bot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.utils.AttachedFile;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class ButtonClickEvent extends ListenerAdapter {

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

        if (event.getButton().getId().equalsIgnoreCase("doubts-course")) {

            Category category = event.getJDA().getCategoryById(1087806779938836563L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-curso: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1087424755902189678L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre o curso**, aguarde dentro de alguns"
                                        + " instantes que algum membro da área de cursos irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o URA foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@&1087424755902189678>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase("doubts-engineer")) {

            Category category = event.getJDA().getCategoryById(1087806779938836563L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-engenharia: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1087423801479942264L), permissions, null)
                    .addPermissionOverride(guild.getRoleById(1087424242255134820L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre as engenharias**, aguarde dentro de alguns"
                                        + " instantes que algum membro da área das engenharias irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o hardware foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@&1087423801479942264>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                        textChannel.sendMessage("<@&1087424242255134820>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase("doubts-marketing")) {

            Category category = event.getJDA().getCategoryById(1087806779938836563L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            guild.createTextChannel("dúvida-marketing: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1087424463869591642L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **dúvidas sobre o marketing**, aguarde dentro de alguns"
                                        + " instantes que algum membro da área de marketing irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua dúvida sobre o software foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendMessage("<@&1087424463869591642>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
                    });

        } else if (event.getButton().getId().equalsIgnoreCase( "doubts-horario")) {

            Category category = event.getJDA().getCategoryById(1087806779938836563L);

            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            ClassLoader loader = getClass().getClassLoader();

            URL resource = loader.getResource("horario.jpeg");

            File file;

            if (resource == null) {
                event.reply("Ops! O horário dos bolsistas não foi encontrado (IllegalArgumentException)").queue();
                return;
            }

            file = new File(resource.getFile());

            guild.createTextChannel("agendamento: " + event.getMember().getUser().getName(), category)
                    .setTopic(event.getMember().getId())
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permissions, null)
                    .addPermissionOverride(guild.getPublicRole(), null, permissions)
                    .addPermissionOverride(guild.getRoleById(1087724592497446922L), permissions, null)
                    .queue(textChannel -> {
                        textChannel.sendMessageEmbeds(new EmbedBuilder()
                                .setDescription("Olá, seja bem-vindo(a) ao ticket de **agendamento de horário**, aguarde dentro de alguns"
                                        + " instantes que algum bolsista do LAR (Laborátório de Automação e Robótica) irá ajudá-lo(a).")
                                .setColor(Color.BLUE).build()).setActionRow(Button.danger("close-doubt", "❌ Fechar ticket")).addContent(event.getMember().getAsMention()).queue();

                        event.reply("Sua solicitação de agendamento foi aberta em " + textChannel.getAsMention() + "!").setEphemeral(true).queue();

                        textChannel.sendFiles(AttachedFile.fromData(file)).queue();

                        textChannel.sendMessage("**Escolha um dos horários acima para poder agendar sua ida ao laboratório!**").queue();

                        textChannel.sendMessage("<@&1087724592497446922>").queue(message -> message.delete().queueAfter(2, TimeUnit.SECONDS));
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

        } else if (event.getButton().getId().equalsIgnoreCase("course-ura-def")) {

            Role role = event.getGuild().getRoleById(1087796556968767639L);

            Member member = event.getMember();

            if (role == null)
                return;

            if (member.getRoles().contains(role)) {
                event.reply("Você já está registrado como membro do curso de graduandos, se deseja trocar, abra uma solicitação para 'Engenharias'.").setEphemeral(true).queue();
                return;
            }

            event.getGuild().addRoleToMember(member, role).queue();

            event.reply("Curso selecionado com sucesso!").setEphemeral(true).queue();

        } else if (event.getButton().getId().equalsIgnoreCase("course-ura-teacher")) {

            Role role = event.getGuild().getRoleById(1087796608969744496L);

            Member member = event.getMember();

            if (role == null)
                return;

            if (member.getRoles().contains(role)) {
                event.reply("Você já está registrado como membro do curso de professores, se deseja trocar, abra uma solicitação para 'Engenharias'.").setEphemeral(true).queue();
                return;
            }

            event.getGuild().addRoleToMember(member, role).queue();

            event.reply("Curso selecionado com sucesso!").setEphemeral(true).queue();
        }
    }
}
