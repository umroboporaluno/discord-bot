package br.natalnet.ura.bot;

import br.natalnet.ura.bot.event.ButtonClickEvent;
import br.natalnet.ura.bot.controller.CommandController;
import br.natalnet.ura.bot.event.MenuClickEvent;
import br.natalnet.ura.bot.listener.DoubtsEmbedMessageListener;
import br.natalnet.ura.bot.listener.RulesEmbedMessageListener;
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
    private static final BotApplication application = new BotApplication();

    @Getter
    private static JDA jda;

    @Getter
    private final String token = "MTA3ODc3OTMzNzkzMjgxNjQ0Ng.G_VfE3.RwZ_golHvyhuvfQkJkbGFYQyAWs4y21JpwozkA";

    @Getter
    private final long discordId = 1078748863705383022L;

    public static void main(String[] args) throws LoginException {

        jda = JDABuilder.createDefault(getApplication().getToken()).setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.streaming("Um robô por aluno", "https://natalnet.github.io/ura/"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .enableCache(CacheFlag.MEMBER_OVERRIDES)
                .addEventListeners(new CommandController(), new ButtonClickEvent(), new MenuClickEvent(), new RulesEmbedMessageListener(), new DoubtsEmbedMessageListener())
                .build();
    }
}
