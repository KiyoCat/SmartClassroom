package de.haw_hamburg.smartclassroom.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.haw_hamburg.smartclassroom.Model.BrightnessObserver;
import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.Model.SmartClassroom;
import de.haw_hamburg.smartclassroom.Model.TemperatureObserver;

public class SmartClassroomController extends ViewModel implements TemperatureObserver, BrightnessObserver {

    private SmartClassroom smartClassroom;

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    private MqttClient mqttClient;

    private MutableLiveData<Integer> seekBarValue = new MutableLiveData<>(0);

    public MutableLiveData<Integer> getSeekBarValue() {
        return this.seekBarValue;
    }

    public void setSeekBarValue(Integer seekBarValue) {
        this.seekBarValue.setValue(seekBarValue);
        sendHeaterValueToServer(seekBarValue);
    }

    public SmartClassroomController() {

    }
//    public SmartClassroomController(SmartClassroom smartClassroom, TemperatureSubscriber temperatureSubscriber, BrightnessSubscriber brightnessSubscriber){
//        this.smartClassroom = smartClassroom;
//        temperatureSubscriber.addObserver(this);
//        brightnessSubscriber.addObserver(this);

   // }

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

    public String sendHeaterValueToServer(int heatingValue) {
        String convertedScale = null;
            try {
                convertedScale = String.valueOf(heatingValue);
                mqttClient.publish("heater", convertedScale);

            }catch (Exception e){
                Log.d("error", "couldn't convert int to String");
            }
            return convertedScale;

        }

    public void rollosSwitchisClicked(boolean isClicked) {
        if(isClicked == true) {
            mqttClient.publish("rollo", "Pull down rollos");
        } else {
            mqttClient.publish("rollo", "Pull up rollos");
        }
    }


}
