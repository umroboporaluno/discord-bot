package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.BotApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

//            case "cadastrar": {
//
//                OptionMapping option = event.getOption("nome");
//
//                String name, rfid;
//
//                MQTT mqtt = BotApplication.getMqtt();
//
//                if (option == null) {
//                    event.reply("Nome inválido").setEphemeral(true).queue();
//                    return;
//                }
//
//                name = option.getAsString();
//
//                option = event.getOption("rfid");
//
//                if (option == null) {
//                    event.reply("RFiD inválido").setEphemeral(true).queue();
//                    return;
//                }
//
//                rfid = option.getAsString();
//
//                Member member = new Member(UUID.randomUUID(), name, rfid);
//
//                String toSend = member.getName() + ";" + member.getRfid();
//
//                mqtt.publish("door/cadastro", toSend.getBytes(), 0, false);
//
//                event.reply("Você cadastrou " + member.getName() + " com sucesso (UUID: " + member.getUuid() + ").").setEphemeral(true).queue();
//
//                break;
//            }

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

        //dataStore.add(Commands.slash("version", "Visualiza a versão atual do BOT."));
        dataStore.add(Commands.slash("horários", "Visualiza os horários do LAR disponíveis para uso."));

        //OptionData arg1 = new OptionData(OptionType.STRING, "nome", "Nome de quem você deseja cadastrar", true);
        //OptionData arg2 = new OptionData(OptionType.STRING, "rfid", "ID RFiD de quem você deseja cadastrar", true);

        //dataStore.add(Commands.slash("cadastrar", "Cadastre uma pessoa para acessar o LAR.").addOptions(arg1, arg2));

        OptionData arg1 = new OptionData(OptionType.STRING, "tópico",  "Tópico do MQTT que você deseja enviar.", true);
        OptionData arg2 = new OptionData(OptionType.STRING, "payload", "Payload do MQTT que você deseja enviar.", true);

        dataStore.add(Commands.slash("publish", "Envie mensagens para o MQTT do LAR.").addOptions(arg1, arg2));

        arg1 = new OptionData(OptionType.INTEGER, "quantidade", "Defina a quantidade de mensagens que você quer apagar.", true);

        dataStore.add(Commands.slash("clear", "Apague um número determinado de mensagens em qualquer chat.").addOptions(arg1));

        event.getGuild().updateCommands().addCommands(dataStore).queue();
    }
}