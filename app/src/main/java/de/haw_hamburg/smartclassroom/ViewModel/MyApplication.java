package de.haw_hamburg.smartclassroom.ViewModel;

import android.app.Application;

import de.haw_hamburg.smartclassroom.Model.MqttHandler;

public class MyApplication extends Application {

    private MqttHandler mqttHandler;

    @Override
    public void onCreate(){
        super.onCreate();
        mqttHandler = new MqttHandler();
        mqttHandler.connect();
    }
    public MqttHandler getMqttHandler(){
        return mqttHandler;
    }
}
