package de.haw_hamburg.smartclassroom.ViewModel;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.haw_hamburg.smartclassroom.Model.BrightnessObserver;
import de.haw_hamburg.smartclassroom.Model.SmartClassroom;
import de.haw_hamburg.smartclassroom.Model.TemperatureObserver;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.View.MainActivity;

public class SmartClassroomController implements TemperatureObserver, BrightnessObserver {

    private SmartClassroom smartClassroom;

    public SmartClassroomController(SmartClassroom smartClassroom, TemperatureSubscriber temperatureSubscriber, BrightnessSubscriber brightnessSubscriber){
        this.smartClassroom = smartClassroom;
        temperatureSubscriber.addObserver(this);
        brightnessSubscriber.addObserver(this);

    }

    @Override
    public void addObserver(MqttObserver observer) {

    }

    @Override
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

    @Override
    public void onMessageReceived(String topic, String message) {

    }

    @Override
    public void onBrightnessChanged(double brightness) {
        smartClassroom.setBrightness(brightness);
    }

    @Override
    public void onTemperatureChanged(double temperature) {
        smartClassroom.setTemperature(temperature);
    }

}
