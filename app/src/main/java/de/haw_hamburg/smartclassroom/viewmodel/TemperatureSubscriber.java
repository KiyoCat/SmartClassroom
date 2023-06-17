package de.haw_hamburg.smartclassroom.viewmodel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.Model.TemperatureObserver;

public class TemperatureSubscriber implements MqttCallback {

    private MqttClient mqttConnection;
    private String channelOfSensor;
    private List<MqttObserver> observers = new ArrayList<>();

    public TemperatureSubscriber(MqttClient mqttConnection, String channelOfSensor){
        this.mqttConnection = mqttConnection;
        this.channelOfSensor = channelOfSensor;
        mqttConnection.getClient().setCallback(this);
    }
    public void addObserver(TemperatureObserver observer){
        this.observers.add(observer);
    }

    public void subscribe(String channel){
        //hier steht basically dasselbe, was im MQTT Handler ist
//        try {
//            this.channelOfSensor = channel;
//            mqttConnection.getClient().subscribe(channel, 0);
//            observers.clear();
//        } catch (MqttException e) {
//            // Handle the exception
//            e.printStackTrace(); // or log the exception details
//        }
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
