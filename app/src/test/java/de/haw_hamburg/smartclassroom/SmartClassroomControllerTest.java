package de.haw_hamburg.smartclassroom;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.viewmodel.SmartClassroomController;

public class SmartClassroomControllerTest {
    @Mock
    private MqttClient mockMqttClient;

    private SmartClassroomController smartClassroomController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        smartClassroomController = new SmartClassroomController();
        smartClassroomController.setMqttClient(mockMqttClient);
    }

    @Test
    public void testSendHeaterValueToServer() {
        int heatingValue = 25;
        String expectedConvertedScale = "25";

        smartClassroomController.sendHeaterValueToServer(heatingValue);

        verify(mockMqttClient).publish(eq("heater"), eq(expectedConvertedScale));
    }

}