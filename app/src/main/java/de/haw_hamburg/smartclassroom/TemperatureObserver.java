package de.haw_hamburg.smartclassroom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface TemperatureObserver extends MqttObserver {

    void addObserver(MqttObserver observer);

    void subscribe(String channel);

    void connectionLost(Throwable cause);

    void messageArrived(String topic, MqttMessage message) throws Exception;

    void deliveryComplete(IMqttDeliveryToken token);

    public void onTemperatureChanged(double temperature);

}
