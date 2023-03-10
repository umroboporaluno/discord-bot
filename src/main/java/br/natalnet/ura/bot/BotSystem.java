package br.natalnet.ura.bot;

import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

@Getter
public class BotSystem {

    private final ShardManager shardManager;

    private final DefaultShardManagerBuilder builder;

    private final String token = "MTA3ODc3OTMzNzkzMjgxNjQ0Ng.G_VfE3.RwZ_golHvyhuvfQkJkbGFYQyAWs4y21JpwozkA";

    private final long discordId = 1078748863705383022L;

    public BotSystem() {
        builder = DefaultShardManagerBuilder.createDefault(getToken());

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Um robô por aluno"));

        shardManager = builder.build();
    }
}
