package br.natalnet;

import br.natalnet.command.RulesCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class BotApplication {

    @Getter
    private static BotApplication application = new BotApplication();

    @Getter
    private static JDA jda;

    @Getter
    private String token = "MTA3ODc3OTMzNzkzMjgxNjQ0Ng.Ga8s0H.0DDVQZ0fduIaunuHaAgvbUgaJ-PF44trjFt-h8";

    @Getter
    private final long discordId = 1078748863705383022L;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(getApplication().getToken()).setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.streaming("Um robô por aluno", "https://natalnet.github.io/ura/"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.MEMBER_OVERRIDES)
                .addEventListeners(new RulesCommand())
                .build();
    }
}
