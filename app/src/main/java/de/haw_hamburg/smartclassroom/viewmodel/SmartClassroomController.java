package de.haw_hamburg.smartclassroom.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.haw_hamburg.smartclassroom.Model.MqttClient;

public class SmartClassroomController extends ViewModel {

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    private MqttClient mqttClient;
    private MutableLiveData<Integer> seekBarValue = new MutableLiveData<>(0);

    public void setSeekBarValue(Integer seekBarValue) {
        this.seekBarValue.setValue(seekBarValue);
        sendHeaterValueToServer(seekBarValue);
    }

    private MutableLiveData<Boolean> switchStateLiveData = new MutableLiveData<>();

    public void setSwitchStateLiveData(Boolean switchState) {
        this.switchStateLiveData.setValue(switchState);
        sendSwitchStateToServer(switchState);
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

    public void sendSwitchStateToServer(boolean switchState) {
        if(switchState==true) {
            mqttClient.publish("rollo", "Pull down");
        } else {
            mqttClient.publish("rollo", "Pull up");
        }
    }


}
