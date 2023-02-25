package br.natalnet.controller;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import okhttp3.Cookie;

import java.awt.*;
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

                event.reply("Nenhum horário ainda foi adicionado no sistema (NullPointerException)").queue();
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> dataStore = new ArrayList<>();

        dataStore.add(Commands.slash("version", "Visualiza a versão atual do BOT."));
        dataStore.add(Commands.slash("horarios", "Visualiza os horários do LAR disponíveis para uso."));

        event.getGuild().updateCommands().addCommands(dataStore).queue();
    }
}