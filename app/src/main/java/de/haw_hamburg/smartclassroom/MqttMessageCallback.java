package de.haw_hamburg.smartclassroom;

public interface MqttMessageCallback {
    void onMessageReceived(String topic, String message);

    void displayMessage(String message);
}
