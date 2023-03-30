package br.natalnet.ura.bot.database.pubsub;

import br.natalnet.ura.bot.BotApplication;
import br.natalnet.ura.bot.BotSystem;
import br.natalnet.ura.bot.database.MQTT;
import br.natalnet.ura.bot.entity.mqtt.MQTTPayloadMessage;
import br.natalnet.ura.bot.entity.mqtt.type.MQTTMessageType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

@Getter
public class MQTTPubSub implements IMqttMessageListener {

    public MQTTPubSub(MQTT mqtt, String topic, int qos) {
        BotApplication.getMqtt().subscribe(qos, this, topic);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

//        TextChannel textChannel = BotApplication.getSystem().getShardManager().getTextChannelById(1087806562686476329L);
//
//        if (textChannel == null)
//            return;
//
//        textChannel.sendMessage(Arrays.toString(message.getPayload())).queue();

        System.out.println(Arrays.toString(message.getPayload()));
    }
}
