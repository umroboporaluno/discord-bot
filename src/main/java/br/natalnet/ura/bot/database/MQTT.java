package br.natalnet.ura.bot.database;

import br.natalnet.ura.bot.BotApplication;
import lombok.Getter;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

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
        this.options.setKeepAliveInterval(5);
        this.options.setAutomaticReconnect(true);
        this.options.setCleanStart(true);

        this.client.connect(options);
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

    public IMqttToken subscribe(int qos, IMqttMessageListener listener, String... topics) {

        if (client == null || topics.length == 0)
            return null;

        int size = topics.length;

        int[] i = new int[size];

        IMqttMessageListener[] listeners = new IMqttMessageListener[size];

        for (int x = 0; x < size; x++) {
            i[x] = qos;

            listeners[x] = listener;
        }

        try {
            return client.subscribe(topics, i, listeners);
        } catch (MqttException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void unsubscribe(String... topics) {

        if (client == null || !client.isConnected() || topics.length == 0)
            return;

        try {
            client.unsubscribe(topics);
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
        System.out.println(message);
    }

    @Override
    public void deliveryComplete(IMqttToken token) {

    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("MQTT " + (reconnect ? "reconnected" : "connected") + " with success in " + serverURI);
    }

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        System.out.println("Connection lost, trying reconnect...");
    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {

    }
}
