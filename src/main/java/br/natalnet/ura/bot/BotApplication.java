package br.natalnet.ura.bot;

import br.natalnet.ura.bot.controller.RedisPubSubController;
import br.natalnet.ura.bot.database.MQTT;
import br.natalnet.ura.bot.database.Redis;
import br.natalnet.ura.bot.database.pubsub.RedisPubSub;
import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class BotApplication {

    @Getter
    private static BotSystem system;

    @Getter
    private static Redis redis;

    @Getter
    private static MQTT mqtt;

    private static RedisPubSub redisPubSub;

    public static void main(String[] args) throws InterruptedException {

        system = new BotSystem();
        redis = new Redis();
        mqtt = new MQTT();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                getSystem().setTicks(getSystem().getTicks() + 1);

                redisPubSub = new RedisPubSub(new RedisPubSubController(), "cadastro");
                redisPubSub.run();

                mqtt.publish("keepalive", String.valueOf(getSystem().getTicks()));

            }

        }, 0, 1);
    }
}
