package br.natalnet.ura.bot.database;

import br.natalnet.ura.bot.controller.MQTTPubSubController;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MQTT {

    private final Mqtt5Client client;

    private final MQTTPubSubController controller;

    public MQTT() throws InterruptedException {

        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("127.0.0.1")
                .build().toBlocking();

        client.toBlocking().connectWith().keepAlive(5).send();

        controller = new MQTTPubSubController();
    }

    public void publish(String topic, String message) {
        client.toBlocking().publishWith().topic(topic).qos(MqttQos.AT_LEAST_ONCE).payload(message.getBytes()).send();
    }

    public void subscribe(String topic) {
        client.toBlocking().subscribeWith().topicFilter(topic).qos(MqttQos.AT_LEAST_ONCE).send();
    }
}
