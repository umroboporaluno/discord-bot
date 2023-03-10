package br.natalnet.ura.bot;

import br.natalnet.ura.bot.database.Redis;
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
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class BotApplication {

    @Getter
    private static BotSystem system;

    @Getter
    private static Redis redis;

    public static void main(String[] args) throws LoginException {
        system = new BotSystem();
        redis = new Redis();
    }
}
