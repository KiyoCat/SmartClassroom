package de.haw_hamburg.smartclassroom.Model;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClient implements MqttCallback {
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

    public org.eclipse.paho.client.mqttv3.MqttClient getClient() {
        return this.client;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        final String receivedMessage = new String(message.getPayload());

        // Update the UI on the main thread
        //activity.runOnUiThread(new Runnable() {
        //    @Override
        //    public void run() {
        //         Update the TextView with the received message
        //        textView.setText(receivedMessage);
        //    }
        //});
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }


}