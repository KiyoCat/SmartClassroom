package de.haw_hamburg.smartclassroom.Model;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MqttClient implements MqttCallback, IMqttMessageListener {
    private org.eclipse.paho.client.mqttv3.MqttClient client;

    public void connect() throws MqttException {
        MqttConnectOptions connectOptions = null;
        try {
            // current broker url for local mosquitto server
            String brokerUrl = "tcp://10.0.2.2";
            String clientId = org.eclipse.paho.client.mqttv3.MqttClient.generateClientId();
            String username = "dignet";
            String password = "digiNetE63";
            MemoryPersistence persistence = new MemoryPersistence();
            client = new org.eclipse.paho.client.mqttv3.MqttClient(brokerUrl, clientId, persistence);

            connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName(username);
            connectOptions.setPassword(password.toCharArray());
            client.setCallback(this);

            client.connect(connectOptions);
            Log.d("success", "connection established.");
        } catch (MqttException e) {
            e.printStackTrace();
            client.connect(connectOptions);
            Log.d("error", "connection failed.");
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
            Log.d("success", "message published.");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("error", "message not published.");
        }
    }

    public void subscribe(String topic) {
        client.setCallback(this);
        try {
            client.subscribe(topic);
            Log.d("success", "subscribed to topic.");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("error", "failed to subscribe to topic.");
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            Log.d("success", "connection closed.");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("error", "failed to disconnect.");
        }
    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            byte[] payload = message.getPayload();
            String convertedMessageContent = new String(payload, StandardCharsets.UTF_8);
            receiveData(topic, convertedMessageContent);
            Log.d("succes", "new message: " + convertedMessageContent);
        } catch (Exception e) {
            Log.d("error", "couldn't handle message");
        }
    };
    private void receiveData(String topic, String message) {
        try {
            Log.d("success", "message received: " + message);

        } catch (Exception e) {
            Log.d("error", "couldn't receive Data");
        }
    }

    public org.eclipse.paho.client.mqttv3.MqttClient getClient() {
        return this.client;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }


}