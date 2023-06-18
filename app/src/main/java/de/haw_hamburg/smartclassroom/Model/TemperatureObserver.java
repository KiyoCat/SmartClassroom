package de.haw_hamburg.smartclassroom.Model;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.haw_hamburg.smartclassroom.viewmodel.MqttObserver;

public interface TemperatureObserver extends MqttObserver {

    void addObserver(MqttObserver observer);

    void subscribe(String channel);

    void connectionLost(Throwable cause);

    void messageArrived(String topic, MqttMessage message) throws Exception;

    void deliveryComplete(IMqttDeliveryToken token);

    public void onTemperatureChanged(double temperature);

}
