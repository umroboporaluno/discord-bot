package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.BotApplication;
import br.natalnet.ura.bot.database.MQTT;
import br.natalnet.ura.bot.entity.door.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSubController extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {

        Gson gson = new GsonBuilder().create();

        if (channel.equals("cadastro")) {

            MQTT mqtt = BotApplication.getMqtt();

            Member member = gson.fromJson(message, Member.class);

            String msg = member.getName() + ";" + member.getRfid();

            mqtt.publish("door/cadastro", msg.getBytes(), 0, false);
        }
    }
}
