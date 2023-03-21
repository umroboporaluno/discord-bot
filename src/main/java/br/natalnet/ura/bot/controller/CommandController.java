package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.BotApplication;
import br.natalnet.ura.bot.entity.Member;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandController extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try (Jedis jedis = BotApplication.getRedis().getJedisPool().getResource()) {
            jedis.setex("last-command", 300, command);
        }

        switch (command) {

            case "version": {
                event.reply("O bot está utilizando a versão 5.0.0-beta.4 JDA (prod-master-ura-bot)").queue();
                break;
            }

            case "horários": {

                ClassLoader loader = getClass().getClassLoader();

                /*
                InputStream stream = loader.getResourceAsStream("horario.png");

                if (stream == null) {
                    event.reply("Ops! O horário dos bolsistas não foi encontrado.").queue();
                } else {
                    event.reply("Arquivo??? (" + stream + ")").queue();
                }
                 */

                URL resource = loader.getResource("horario.jpeg");

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

                break;
            }

            case "clear": {

                if (event.getMember() == null)
                    return;

                if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
                    event.reply("Você não tem permissão para fazer isso.").queue();

                event.getChannel().getHistory().retrievePast(100).queue(messages -> {
                    messages.forEach(message -> message.delete().submit());
                });

                event.reply("Você limpou 100 mensagens deste chat.").setEphemeral(true).queue();

                break;
            }

            case "cadastrar": {

                OptionMapping option = event.getOption("nome");

                String name, role, rfid;

                Gson gson = new GsonBuilder().create();

                if (option == null) {
                    event.reply("Nome inválido").setEphemeral(true).queue();
                    return;
                }

                name = option.getAsString();

                option = event.getOption("cargo");

                if (option == null) {
                    event.reply("Cargo inválido").setEphemeral(true).queue();
                    return;
                }

                role = option.getAsString();

                option = event.getOption("rfid");

                if (option == null) {
                    event.reply("RFiD inválido").setEphemeral(true).queue();
                    return;
                }

                rfid = option.getAsString();

                Member member = new Member(UUID.randomUUID(), name, role, rfid);

                System.out.println(member.getUuid());

                try (Jedis jedis = BotApplication.getRedis().getJedisPool().getResource()) {
                    jedis.setex(member.getUuid().toString(), 300, gson.toJson(member));

                    jedis.publish("cadastro", gson.toJson(member));
                }

                event.reply("Você cadastrou " + member.getName() + " com sucesso (UUID: " + member.getUuid() + ").").queue();

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

            case "ultima": {

                if (event.getMember() == null)
                    return;

                try (Jedis jedis = BotApplication.getRedis().getJedisPool().getResource()) {

                    if (jedis.get("last-command") == null) {
                        event.reply("Não foi executado nenhum comando pelo bot pelos últimos 5 minutos.").queue();
                        return;
                    }

                    event.reply("O último comando executado foi '/" + jedis.get("last-command") + "'.").queue();
                }

                break;
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> dataStore = new ArrayList<>();

        dataStore.add(Commands.slash("version", "Visualiza a versão atual do BOT."));
        dataStore.add(Commands.slash("horários", "Visualiza os horários do LAR disponíveis para uso."));
        dataStore.add(Commands.slash("clear", "Limpa as últimas 100 mensagens em qualquer chat."));
        dataStore.add(Commands.slash("carros", "Visualiza os carros salvos no banco de dados."));
        dataStore.add(Commands.slash("ultima", "Visualiza o último comando executado num período de 5 minutos."));

        OptionData arg1 = new OptionData(OptionType.STRING, "nome", "Nome de quem você deseja cadastrar", true);
        OptionData arg2 = new OptionData(OptionType.STRING, "cargo", "Cargo de quem você deseja cadastrar", true);
        OptionData arg3 = new OptionData(OptionType.STRING, "rfid", "ID RFiD de quem você deseja cadastrar", true);

        dataStore.add(Commands.slash("cadastrar", "Cadastre uma pessoa para acessar o LAR.").addOptions(arg1, arg2, arg3));

        arg1 = new OptionData(OptionType.STRING, "tópico",  "Tópico do MQTT que você deseja enviar.", true);
        arg2 = new OptionData(OptionType.STRING, "payload", "Payload do MQTT que você deseja enviar.", true);

        dataStore.add(Commands.slash("publish", "Envie mensagens para o MQTT do LAR.").addOptions(arg1, arg2));

        event.getGuild().updateCommands().addCommands(dataStore).queue();
    }
}