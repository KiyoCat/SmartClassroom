package de.haw_hamburg.smartclassroom.ViewModel;

public interface MqttObserver {
    void onMessageReceived(String topic, String message);
}
