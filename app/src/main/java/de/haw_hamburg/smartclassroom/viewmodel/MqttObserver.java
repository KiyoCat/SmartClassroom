package de.haw_hamburg.smartclassroom.viewmodel;

public interface MqttObserver {
    void onMessageReceived(String topic, String message);
}
