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

        mqtt = new MQTT("tcp://127.0.0.1:1883");
        mqtt.connect();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                getSystem().setTicks(getSystem().getTicks() + 1);

                System.out.println(getSystem().getTicks());

                redisPubSub = new RedisPubSub(new RedisPubSubController(), "cadastro");
                redisPubSub.run();

            }
        }, 0, 1);
    }
}
