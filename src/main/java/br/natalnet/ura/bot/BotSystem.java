package br.natalnet.ura.bot;

import br.natalnet.ura.bot.controller.CommandController;
import br.natalnet.ura.bot.event.ButtonClickEvent;
import br.natalnet.ura.bot.event.MenuClickEvent;
import br.natalnet.ura.bot.listener.CourseSelectEmbedMessageListener;
import br.natalnet.ura.bot.listener.DoubtsEmbedMessageListener;
import br.natalnet.ura.bot.listener.RulesEmbedMessageListener;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@Getter
public class BotSystem {

    private final Dotenv dotenv = Dotenv.configure().directory("/root/bot/").load();

    private final ShardManager shardManager;

    private final DefaultShardManagerBuilder builder;

    private final String token = dotenv.get("DISCORD_TOKEN");

    private final String mqttAddress = dotenv.get("MQTT_ADDRESS");

    private final long discordId = 630862350827782144L;

    public BotSystem() {
        builder = DefaultShardManagerBuilder.createDefault(getToken());

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Um robô por aluno"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES);

        shardManager = builder.build();

        shardManager.addEventListener(new DoubtsEmbedMessageListener(), new RulesEmbedMessageListener(), new CourseSelectEmbedMessageListener());
        shardManager.addEventListener(new CommandController());
        shardManager.addEventListener(new ButtonClickEvent(), new MenuClickEvent());
    }
}
