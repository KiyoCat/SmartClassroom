package de.haw_hamburg.smartclassroom.ViewModel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.smartclassroom.Model.TemperatureObserver;

public class TemperatureSubscriber implements MqttCallback {

    private MqttHandler mqttConnection;
    private String channelOfSensor;
    private List<MqttObserver> observers = new ArrayList<>();

    public TemperatureSubscriber(MqttHandler mqttConnection, String channelOfSensor){
        this.mqttConnection = mqttConnection;
        this.channelOfSensor = channelOfSensor;
        mqttConnection.getClient().setCallback(this);
    }
    public void addObserver(TemperatureObserver observer){
        this.observers.add(observer);
    }

    public void subscribe(String channel){

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
