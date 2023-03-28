package br.natalnet.ura.bot.entity.mqtt;

import br.natalnet.ura.bot.entity.mqtt.type.MQTTMessageType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MQTTPayloadMessage {

    private final String topic, message;

    private final MQTTMessageType type;

    @Override
    public String toString() {
        return "MQTTPayloadMessage{" +
                "topic='" + topic + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
