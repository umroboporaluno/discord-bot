package br.natalnet.ura.bot.controller;

import br.natalnet.ura.bot.entity.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSubController extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {

        Gson gson = new GsonBuilder().create();

        if (channel.equals("cadastro")) {

            Member member = gson.fromJson(message, Member.class);

            System.out.println(member.toString());
        }
    }
}
