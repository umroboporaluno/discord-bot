package br.natalnet.ura.bot.database.pubsub;

import br.natalnet.ura.bot.BotApplication;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Getter
public class RedisPubSub implements Runnable {

    private final JedisPubSub jedisPubSub;

    private final String[] channels;

    public RedisPubSub(JedisPubSub jedisPubSub, String... channels) {
        this.jedisPubSub = jedisPubSub;
        this.channels = channels;
    }

    @Override
    public void run() {
        boolean connected = true;

        try (Jedis jedis = BotApplication.getRedis().getJedisPool().getResource()) {
            jedis.subscribe(jedisPubSub, channels);
        } catch (Exception e) {
            connected = false;
        }

        if (!connected) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            run();
        }
    }
}
