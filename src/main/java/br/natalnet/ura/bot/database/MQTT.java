package br.natalnet.ura.bot.database;

import br.natalnet.ura.bot.BotApplication;
import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.UUID;

@Getter
public class MQTT implements MqttCallback, IMqttMessageListener {

    private final String serverUri;

    private final MqttClient client;

    private final MqttConnectionOptions options;

    public MQTT(String serverUri) throws MqttException {
        this.serverUri = serverUri;

        this.client = new MqttClient(serverUri, UUID.randomUUID().toString(), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));

        this.options = new MqttConnectionOptions();

        this.options.setUserName("mqtt");
        this.options.setPassword("lar_mqtt".getBytes());
        this.options.setConnectionTimeout(0);
        this.options.setKeepAliveInterval(60);
        this.options.setAutomaticReconnect(true);
        this.options.setCleanStart(false);

        this.client.connect(options);
        this.client.setCallback(this);
        this.client.subscribe("#", 0);
    }

    public void disconnect() {

        if (client == null || !client.isConnected())
            return;

        try {

            client.disconnect();
            client.close();

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public synchronized void publish(String topic, byte[] payload, int qos, boolean retained) {

        try {

            if (!client.isConnected())
                return;

            client.publish(topic, payload, qos, retained);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {
        System.out.println(exception.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttToken token) {

    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("MQTT " + (reconnect ? "reconnected" : "connected") + " with success in " + serverURI);

        try {

            client.connect(options);
            client.setCallback(this);
            client.subscribe("#", 0);

        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        System.out.println("Connection lost, trying reconnect...");
    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {

    }
}
