package br.natalnet.ura.bot.database;

import com.google.gson.JsonObject;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.function.Consumer;

@Getter
public class Redis implements AutoCloseable {

    private JedisPool jedisPool;

    public Redis() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(128);
        config.setMaxWaitMillis(10000);
        config.setMaxIdle(20);
        config.setMinIdle(5);
        config.setBlockWhenExhausted(false);

        jedisPool = new JedisPool(config, "10.6.1.51", 6379, 5000);
    }

    public void publish(String channel, String type, Consumer<JsonObject> jsonObject) {
        JsonObject message = new JsonObject();

        message.addProperty("type", type);

        JsonObject data = new JsonObject();

        jsonObject.accept(data);

        message.add("data", data);

        try (Jedis jedis = getJedisPool().getResource()) {
            jedis.publish(channel, message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String channel, String message) {
        try (Jedis jedis = getJedisPool().getResource()) {
            jedis.publish(channel, message);
        }
    }

    @Override
    public void close() throws Exception {
        jedisPool.close();

        jedisPool = null;
    }
}
