package de.haw_hamburg.smartclassroom;

public interface MqttObserver {
    void onMessageReceived(String topic, String message);
}
