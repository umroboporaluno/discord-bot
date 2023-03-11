package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.BotApplication;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.util.concurrent.TimeUnit;

public class MQTTPubSubController {

    public void handleSubscribe() throws InterruptedException {

        try (Mqtt5BlockingClient.Mqtt5Publishes publishes = BotApplication.getMqtt().getClient().toBlocking().publishes(MqttGlobalPublishFilter.ALL)) {

            publishes.receive(1, TimeUnit.SECONDS).ifPresent(System.out::println);
        }
    }
}
