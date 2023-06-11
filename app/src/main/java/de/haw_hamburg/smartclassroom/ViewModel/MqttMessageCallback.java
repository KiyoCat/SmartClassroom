package de.haw_hamburg.smartclassroom.ViewModel;

public interface MqttMessageCallback {
    void onMessageReceived(String topic, String message);

}
