package de.haw_hamburg.smartclassroom;

/*import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;

import de.haw_hamburg.smartclassroom.Model.MqttClient;

public class MqttClientTest implements IMqttMessageListener {
    @Mock
    private org.eclipse.paho.client.mqttv3.MqttClient mockClient;

    private MqttClient mqttClient;
    private String receivedTopic;
    private String receivedMessage;

    @Before
    public void setUp() throws MqttException {
        MockitoAnnotations.initMocks(this);
        mqttClient = new MqttClient();
        mqttClient.client = mockClient;
    }

    @Test
    public void testConnect_SuccessfulConnection() throws MqttException {
        // Arrange
        String brokerUrl = "tcp://10.0.2.2";
        String clientId = "testClientId";
        String username = "dignet";
        String password = "digiNetE63";
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());

        // Act
        mqttClient.connect();

        // Assert
        verify(mockClient).connect(eq(connectOptions));
        verify(mockClient, times(1)).connect(any(MqttConnectOptions.class));
    }

    @Test(expected = MqttException.class)
    public void testConnect_ConnectionFailed() throws MqttException {
        // Arrange
        String brokerUrl = "tcp://10.0.2.2";
        String clientId = "testClientId";
        String username = "dignet";
        String password = "digiNetE63";
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());

        doThrow(new MqttException(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED)).when(mockClient).connect(any(MqttConnectOptions.class));

        // Act
        mqttClient.connect();

        // Assert isn't needed, because MqttException is expected
    }

    @Test
    public void testPublish_SuccessfulPublication() throws MqttException {
        // Arrange
        String topic = "test/testingMqttClient";
        String message = "testing: testPublish_SuccessfulPublication";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());

        // Act
        mqttClient.publish(topic, message);

        // Assert
        verify(mockClient).publish(eq(topic), eq(mqttMessage));
        verify(mockClient, times(1)).publish(anyString(), any(MqttMessage.class));
    }

    @Test
    public void testPublish_MqttException() throws MqttException {
        // Arrange
        String topic = "test/testingMqttClient";
        String message = "testing: testPublish_MqttException";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());

        doThrow(new MqttException(MqttException.REASON_CODE_CLIENT_EXCEPTION)).when(mockClient).publish(anyString(), any(MqttMessage.class));

        // Act
        mqttClient.publish(topic, message);

        // Assert
        // verify that an MqttException is caught and logged
        verify(mockClient).publish(eq(topic), eq(mqttMessage));
        verify(mockClient, times(1)).publish(anyString(), any(MqttMessage.class));
    }

    @Test
    public void testSubscribe_SuccessfulSubscription() throws MqttException {
        // Arrange
        String topic = "test/testingMqttClient";

        // Act
        mqttClient.subscribe(topic);

        // Assert
        verify(mockClient).setCallback(eq(mqttClient));
        verify(mockClient).subscribe(eq(topic));
        verify(mockClient, times(1)).subscribe(anyString());
    }

    @Test
    public void testSubscribe_MqttException() throws MqttException {
        // Arrange
        String topic = "test/testingMqttClient";

        doThrow(new MqttException(MqttException.REASON_CODE_CLIENT_EXCEPTION)).when(mockClient).subscribe(anyString());

        // Act
        mqttClient.subscribe(topic);

        // Assert
        verify(mockClient).setCallback(eq(mqttClient));
        verify(mockClient).subscribe(eq(topic));
        verify(mockClient, times(1)).subscribe(anyString());
    }

    @Test
    public void testDisconnect_SuccessfulDisconnection() throws MqttException {
        // Act
        mqttClient.disconnect();

        // Assert
        verify(mockClient).disconnect();
        verify(mockClient, times(1)).disconnect();
    }

    @Test
    public void testDisconnect_MqttException() throws MqttException {
        // Arrange
        doThrow(new MqttException(MqttException.REASON_CODE_CLIENT_EXCEPTION)).when(mockClient).disconnect();

        // Act
        mqttClient.disconnect();

        // Assert
        verify(mockClient).disconnect();
        verify(mockClient, times(1)).disconnect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        byte[] payload = message.getPayload();
        String convertedMessageContent = new String(payload, StandardCharsets.UTF_8);
        receiveData(topic, convertedMessageContent);
    }

    private void receiveData(String topic, String message) {
        this.receivedTopic = topic;
        this.receivedMessage = message;
    }

    @Test
    public void testMessageArrived_SuccessfulHandling() throws Exception {
        // Arrange
        String topic = "test/testingMqttClient";
        String messageContent = "testing: testMessageArrived_SuccessfulHandling";
        byte[] payload = messageContent.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);

        // Act
        mqttClient.messageArrived(topic, mqttMessage);

        // Assert
        assertEquals(topic, receivedTopic);
        assertEquals(messageContent, receivedMessage);
    }

    public void testGetClient() {
        // Act
        org.eclipse.paho.client.mqttv3.MqttClient result = mqttClient.getClient();

        // Assert
        assertEquals(mockClient, result);
    }

    @Test
    public void testDeliveryComplete() {
        // Arrange
        IMqttDeliveryToken mockToken = mock(IMqttDeliveryToken.class);

        // Act
        mqttClient.deliveryComplete(mockToken);

        // Assert
        // not needed, because the method has no logic
    }

    @Test
    public void testConnectionLost() {
        // Arrange
        Throwable mockCause = mock(Throwable.class);

        // Act
        mqttClient.connectionLost(mockCause);

        // Assert
        // no logic
    }

}*/