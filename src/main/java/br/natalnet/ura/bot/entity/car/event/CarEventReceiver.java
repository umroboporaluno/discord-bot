package br.natalnet.ura.bot.entity.car.event;

import br.natalnet.ura.bot.entity.car.Car;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.util.ArrayList;

public class CarEventReceiver implements IMqttMessageListener {

    private ArrayList<Car> cars;

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        if (!topic.equals("ura/car")) {
            return;
        }
    }
}
