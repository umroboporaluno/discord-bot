package br.natalnet.ura.bot;

import br.natalnet.ura.bot.controller.CommandController;
import br.natalnet.ura.bot.event.ButtonClickEvent;
import br.natalnet.ura.bot.event.MenuClickEvent;
import br.natalnet.ura.bot.listener.DoubtsEmbedMessageListener;
import br.natalnet.ura.bot.listener.RulesEmbedMessageListener;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@Getter
public class BotSystem {

    private final ShardManager shardManager;

    private final DefaultShardManagerBuilder builder;

    private final String token = "MTA3ODc3OTMzNzkzMjgxNjQ0Ng.G_VfE3.RwZ_golHvyhuvfQkJkbGFYQyAWs4y21JpwozkA";

    private final long discordId = 1078748863705383022L;

    @Setter
    private int ticks;

    public BotSystem() {
        builder = DefaultShardManagerBuilder.createDefault(getToken());

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Um robô por aluno"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES);

        shardManager = builder.build();

        shardManager.addEventListener(new DoubtsEmbedMessageListener(), new RulesEmbedMessageListener());
        shardManager.addEventListener(new CommandController());
        shardManager.addEventListener(new ButtonClickEvent(), new MenuClickEvent());
    }
}
