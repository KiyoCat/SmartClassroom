package de.haw_hamburg.smartclassroom.ViewModel;

public interface MqttMessageCallback {
    String onMessageReceived(String topic, String message) throws Exception;

}
