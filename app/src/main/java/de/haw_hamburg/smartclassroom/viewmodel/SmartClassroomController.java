package de.haw_hamburg.smartclassroom.viewmodel;

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
    private MqttClient mqttClient;

    private MutableLiveData<Integer> seekBarValue = new MutableLiveData<>(0);

    public Integer getSeekBarValue() {
        return this.seekBarValue.getValue();
    }

    public void setSeekBarValue(Integer seekBarValue) {
        this.seekBarValue.setValue(seekBarValue);
    }

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

    public void sendHeaterValueToServer(int heatingValue) {
        switch(heatingValue) {
            case 0:
                mqttClient.publish("Temperature", "Heater on 0");
                break;
            case 1:
                mqttClient.publish("Temperature", "Heater on 1");
                break;
            case 2:
                mqttClient.publish("Temperature", "Heater on 2");
                break;
            case 3:
                mqttClient.publish("Temperature", "Heater on 3");
                break;
            case 4:
                mqttClient.publish("Temperature", "Heater on 4");
                break;
            case 5:
                mqttClient.publish("Temperature", "Heater on 5");
                break;
        }
    }

    public void rollosSwitchisClicked(boolean isClicked) {
        if(isClicked == true) {
            mqttClient.publish("Brightness", "Pull down Rollos");
        } else {
            mqttClient.publish("Brightness", "Pull up Rollos");
        }
    }


}
