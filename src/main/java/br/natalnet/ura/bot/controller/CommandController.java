package br.natalnet.ura.bot.controller;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.AttachedFile;
import net.dv8tion.jda.api.utils.FileUpload;
import okhttp3.Cookie;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommandController extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        switch (command) {

            case "version" -> {

                event.reply("O bot está utilizando a versão 5.0.0-beta.4 JDA (prod-master-ura-bot)").queue();
            }

            case "horarios" -> {

                ClassLoader loader = getClass().getClassLoader();

                /*
                InputStream stream = loader.getResourceAsStream("horario.png");

                if (stream == null) {
                    event.reply("Ops! O horário dos bolsistas não foi encontrado.").queue();
                } else {
                    event.reply("Arquivo??? (" + stream + ")").queue();
                }
                 */

                URL resource = loader.getResource("horario.png");

                if (resource == null) {

                    event.reply("Ops! O horário dos bolsistas não foi encontrado (IllegalArgumentException)").queue();

                } else {

                    try {

                        File file = new File(resource.getFile());

                        event.replyFiles(AttachedFile.fromData(file)).queue();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            case "clear" -> {

                if (event.getMember() == null)
                    return;

                if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
                    event.reply("Você não tem permissão para fazer isso.").queue();

                event.getChannel().getHistory().retrievePast(100).queue(messages -> {
                    messages.forEach(message -> message.delete().submit());
                });

                event.reply("Você limpou 100 mensagens deste chat.").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> dataStore = new ArrayList<>();

        dataStore.add(Commands.slash("version", "Visualiza a versão atual do BOT."));
        dataStore.add(Commands.slash("horarios", "Visualiza os horários do LAR disponíveis para uso."));
        dataStore.add(Commands.slash("clear", "Limpa as últimas 100 mensagens em qualquer chat").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));

        event.getGuild().updateCommands().addCommands(dataStore).queue();
    }
}