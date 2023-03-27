package br.natalnet.ura.bot.database;

import lombok.Getter;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import java.util.UUID;

@Getter
public class MQTT implements MqttCallback {

    private final String serverUri;

    private MqttClient client;

    private final MqttConnectionOptions options;

    public MQTT(String serverUri) {
        this.serverUri = serverUri;

        this.options = new MqttConnectionOptions();

        options.setUserName("mqtt");
        options.setPassword("lar_mqtt".getBytes());
        options.setConnectionTimeout(3);
        options.setKeepAliveInterval(5);
        options.setAutomaticReconnect(true);
        options.setCleanStart(false);
    }

    public void connect() {
        try {
            System.out.println("Connecting to " + getServerUri() + " MQTT broker...");

            client = new MqttClient(serverUri, UUID.randomUUID().toString(), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));

            client.setCallback(this);

            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
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
