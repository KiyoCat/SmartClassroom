package de.haw_hamburg.smartclassroom;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.viewmodel.SmartClassroomController;

import static org.mockito.Mockito.*;

public class SmartClassroomControllerTest {

    @Mock
    private MqttClient mqttClient;

    private SmartClassroomController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new SmartClassroomController();
        controller.setMqttClient(mqttClient);
    }

    @Test
    public void testSendHeaterValueToServer() throws MqttException {
        int heatingValue = 3;
        String expectedMessage = "3";

        controller.sendHeaterValueToServer(heatingValue);

        verify(mqttClient, times(1)).publish(eq("heater"), eq(expectedMessage));
    }

    @Test
    public void testSendSwitchStateToServerTrue() throws MqttException {
        boolean switchState = true;
        String expectedMessage = "Pull down";

        controller.sendSwitchStateToServer(switchState);

        verify(mqttClient, times(1)).publish(eq("rollo"), eq(expectedMessage));
    }

    @Test
    public void testSendSwitchStateToServerFalse() throws MqttException {
        boolean switchState = false;
        String expectedMessage = "Pull up";

        controller.sendSwitchStateToServer(switchState);

        verify(mqttClient, times(1)).publish(eq("rollo"), eq(expectedMessage));
    }
}