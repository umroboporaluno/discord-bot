package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.BotApplication;
import br.natalnet.ura.bot.message.WelcomeMessagePattern;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.AttachedFile;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandController extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        switch (command) {

            case "horários": {

                File file = new File(File.separator + System.getProperty("user.home") + File.separator + "bot" + File.separator, "horarios.jpeg");

                event.replyFiles(AttachedFile.fromData(file)).queue();

                break;
            }

            case "bemvindo": {

                if (event.getMember() == null)
                    return;

                if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
                    event.reply("Você não tem permissão para fazer isso.").setEphemeral(true).queue();
                }

                OptionMapping option = event.getOption("curso");

                if (option == null) {
                    event.reply("Você precisa inserir um curso para executar este comando!").setEphemeral(true).queue();
                    return;
                }

                WelcomeMessagePattern pattern = WelcomeMessagePattern.valueOf(option.getAsString().toUpperCase());

                EmbedBuilder builder = new EmbedBuilder().setAuthor(pattern.getTitle())
                        .setDescription(pattern.getSubtitle())
                        .setFooter("Ficamos felizes com sua participação no curso de robótica!");

                event.getMessageChannel().sendMessageEmbeds(builder.build()).queue();

                break;
            }

            case "clear": {

                if (event.getMember() == null)
                    return;

                if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
                    event.reply("Você não tem permissão para fazer isso.").queue();

                OptionMapping option = event.getOption("quantidade");

                int msgs;

                if (option == null) {
                    event.reply("Quantidade de mensagens inválida").setEphemeral(true).queue();
                    return;
                }

                msgs = option.getAsInt();

                if (msgs >= 200) {
                    event.reply("O limite máximo é de 199 mensagens!").setEphemeral(true).queue();
                    return;
                }

                event.getChannel().getHistory().retrievePast(msgs).queue(messages -> {
                    messages.forEach(message -> message.delete().submit());
                });

                event.reply("Você limpou " + msgs + " mensagens deste chat.").setEphemeral(true).queue();

                break;
            }

            case "publish": {

                OptionMapping option = event.getOption("tópico");

                String topic, payload;

                Gson gson = new GsonBuilder().create();

                if (option == null) {
                    event.reply("Tópico inválido").setEphemeral(true).queue();
                    return;
                }

                topic = option.getAsString();

                option = event.getOption("payload");

                if (option == null) {
                    event.reply("Payload inválido").setEphemeral(true).queue();
                    return;
                }

                payload = option.getAsString();

                BotApplication.getMqtt().publish(topic, payload.getBytes(), 0, false);

                event.reply("Você publicou a mensagem " + gson.toJson(payload) + " no tópico " + topic + " (MQTT URI: " + BotApplication.getMqtt().getServerUri() + ").").queue();

                break;
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> dataStore = new ArrayList<>();

        dataStore.add(Commands.slash("horários", "Visualiza os horários do LAR disponíveis para uso."));

        OptionData arg1 = new OptionData(OptionType.STRING, "tópico",  "Tópico do MQTT que você deseja enviar.", true);
        OptionData arg2 = new OptionData(OptionType.STRING, "payload", "Payload do MQTT que você deseja enviar.", true);

        dataStore.add(Commands.slash("publish", "Envie mensagens para o MQTT do LAR.").addOptions(arg1, arg2));

        arg1 = new OptionData(OptionType.INTEGER, "quantidade", "Defina a quantidade de mensagens que você quer apagar.", true);

        dataStore.add(Commands.slash("clear", "Apague um número determinado de mensagens em qualquer chat.").addOptions(arg1));

        arg1 = new OptionData(OptionType.STRING, "curso", "Insira o curso que você deseja enviar a mensagem de boas-vindas.", true);

        dataStore.add(Commands.slash("bemvindo", "Envie uma mensagem de boas vindas para os membros do curso.").addOptions(arg1));

        event.getGuild().updateCommands().addCommands(dataStore).queue();
    }
}