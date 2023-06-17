package de.haw_hamburg.smartclassroom.ViewModel;

import android.app.Application;

import org.eclipse.paho.client.mqttv3.MqttException;

import de.haw_hamburg.smartclassroom.Model.MqttClient;

public class MyApplication extends Application {

    private MqttClient mqttClient;

    @Override
    public void onCreate(){
        super.onCreate();
        mqttClient = new MqttClient();
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public MqttClient getMqttHandler(){
        return mqttClient;
    }
}
