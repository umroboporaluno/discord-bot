package br.natalnet.ura.bot.database.properties;

import br.natalnet.ura.bot.BotProperties;
import br.natalnet.ura.bot.database.properties.list.MQTTProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MQTTProperties extends BotProperties {

    protected final Gson gson = new GsonBuilder().create();

    @Override
    public void load(File file) {
        MQTTProperty property = new MQTTProperty("tcp://127.0.0.1");

        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(gson.toJson(property));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            JSONParser jsonParser = new JSONParser();

            try {
                property = gson.fromJson(((JSONObject) jsonParser.parse(new FileReader(file))).toJSONString(), MQTTProperty.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unload(File file) {

    }
}
