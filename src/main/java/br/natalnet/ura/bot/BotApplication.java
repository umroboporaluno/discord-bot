package br.natalnet.ura.bot;

import br.natalnet.ura.bot.controller.RedisPubSubController;
import br.natalnet.ura.bot.database.MQTT;
import br.natalnet.ura.bot.database.Redis;
import br.natalnet.ura.bot.database.pubsub.MQTTPubSub;
import br.natalnet.ura.bot.database.pubsub.RedisPubSub;
import lombok.Getter;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.util.*;

public class BotApplication {

    @Getter
    private static BotSystem system;

    @Getter
    private static Redis redis;

    @Getter
    private static MQTT mqtt;

    private static RedisPubSub redisPubSub;

    @Getter
    private static MQTTPubSub mqttPubSub;

    public static void main(String[] args) throws MqttException {

        system = new BotSystem();
        redis = new Redis();

        redisPubSub = new RedisPubSub(new RedisPubSubController(), "cadastro");

        mqtt = new MQTT("tcp://10.6.1.42:1883");
        mqtt.connect();

        mqttPubSub = new MQTTPubSub(mqtt, "#", 0);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                redisPubSub.run();
            }
        };

        timer.schedule(task, 1, 1);
    }
}
