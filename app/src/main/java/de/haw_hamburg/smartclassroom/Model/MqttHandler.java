package de.haw_hamburg.smartclassroom.Model;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import de.haw_hamburg.smartclassroom.ViewModel.MqttMessageCallback;

public class MqttHandler implements MqttMessageCallback {

    private MqttClient client;
    private MqttMessageCallback messageCallback;

    public void setMessageCallback(MqttMessageCallback callback){
        this.messageCallback = callback;
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception{
        if (messageCallback != null){
            messageCallback.onMessageReceived(topic, message.toString());
        }
    }

    public void connect() {
        try {
            String brokerUrl = "tcp://10.0.2.2";
            String clientId = MqttClient.generateClientId();
            String username = "dignet";
            String password = "digiNetE63";
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(brokerUrl, clientId, persistence);

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName(username);
            connectOptions.setPassword(password.toCharArray());

            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient(){
        return this.client;
    }

    @Override
    public void onMessageReceived(String topic, String message) {

    }
}