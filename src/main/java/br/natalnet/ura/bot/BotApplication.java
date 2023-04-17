package br.natalnet.ura.bot;

import br.natalnet.ura.bot.controller.RedisPubSubController;
import br.natalnet.ura.bot.database.MQTT;
import br.natalnet.ura.bot.database.Redis;
import br.natalnet.ura.bot.database.pubsub.RedisPubSub;
import lombok.Getter;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.*;

public class BotApplication {

    @Getter
    private static BotSystem system;

    @Getter
    private static Redis redis;

    @Getter
    private static MQTT mqtt;

    private static RedisPubSub redisPubSub;

    private static int tick;

    public static void main(String[] args) throws MqttException {

        system = new BotSystem();
        redis = new Redis();

        redisPubSub = new RedisPubSub(new RedisPubSubController(), "cadastro");

        mqtt = new MQTT("tcp://10.6.1.42:1883");

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                tick++;

                redisPubSub.run();

                if (tick % 60 == 0) {
                    mqtt.publish("bot/keepalive", ("KeepAlive: " + tick).getBytes(), 1, false);
                }

                System.out.println(tick);
            }
        };

        timer.schedule(task, 1, 1);
    }
}
