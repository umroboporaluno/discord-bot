package br.natalnet.ura.bot;

import br.natalnet.ura.bot.database.Redis;
import lombok.Getter;

import javax.security.auth.login.LoginException;

public class BotApplication {

    @Getter
    private static BotSystem system;

    @Getter
    private static Redis redis;

    public static void main(String[] args) {
        system = new BotSystem();
        redis = new Redis();
    }
}
