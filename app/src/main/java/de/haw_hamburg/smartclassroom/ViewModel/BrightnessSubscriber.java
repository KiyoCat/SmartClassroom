package de.haw_hamburg.smartclassroom.ViewModel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.smartclassroom.Model.BrightnessObserver;

public class BrightnessSubscriber implements MqttCallback {

    private MqttHandler mqttConnection;
    private String channelOfSensor;
    private List<MqttObserver> observers = new ArrayList<>();

    public BrightnessSubscriber(MqttConnect mqttConnect, String channelOfSensor){
        this.mqttConnection = mqttConnection;
        this.channelOfSensor = channelOfSensor;
        mqttConnection.getClient().setCallback(this);
    }
    public void addObserver(BrightnessObserver observer){
        this.observers.add(observer);
    }

    public void subscribe(String channel) {

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
