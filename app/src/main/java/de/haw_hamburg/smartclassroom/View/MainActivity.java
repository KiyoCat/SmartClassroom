package de.haw_hamburg.smartclassroom.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.Model.SmartClassroom;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.viewmodel.MyApplication;

public class MainActivity extends AppCompatActivity {

    private SmartClassroom smartClassroom;
    Button button;
    ImageView imageView;
    TextView temperatureTextView;

    TextView temperatureTextViewE62;
    TextView temperatureTextViewE63;
    TextView brightnessTextView;
    TextView brightnessTextViewE62;
    TextView brightnessTextViewE63;

    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication myApp = (MyApplication) getApplication();
        mqttClient = myApp.getMqttHandler();
        try {
            mqttClient.connect();
            mqttClient.subscribe("temperature");
            mqttClient.subscribe("brightness");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.e("error", "couldn't connect in MainActivity");
        }

        button = (Button) findViewById(R.id.roomActivity_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoomActivity();
            }
        });

        imageView = (ImageView) findViewById(R.id.newRoom_ImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewRoomActivity();
            }
        });

        temperatureTextView = (TextView) findViewById(R.id.temperatureCelsius_MaterialTextView);
        temperatureTextViewE62 = (TextView) findViewById(R.id.temperatureCelsiusE62_MaterialTextView);
        temperatureTextViewE63 = (TextView) findViewById(R.id.temperatureCelsiusE63_MaterialTextView);
        brightnessTextView = (TextView) findViewById(R.id.lightPercentage_MaterialTextView);
        brightnessTextViewE62 = (TextView) findViewById(R.id.lightPercentageE62_MaterialTextView);
        brightnessTextViewE63 = (TextView) findViewById(R.id.lightPercentageE63_MaterialTextView);


        // testing, ignore pls
        mqttClient.subscribe("test");
        mqttClient.publish("test", "hallo test");


    }

    @Override
    protected void onResume() {
        super.onResume();
        mqttClient.getClient().setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("temperature")) {

                    String receivedMessage = new String(message.getPayload(), StandardCharsets.UTF_8);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temperatureTextView.setText(receivedMessage + "°C");
                            temperatureTextViewE62.setText(receivedMessage + "°C");
                            temperatureTextViewE63.setText(receivedMessage + "°C");
                        }
                    });
                }else if(topic.equals("brightness")){
                    String receivedMessage = new String(message.getPayload(), StandardCharsets.UTF_8);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //probably still need to calculate the percentage, not sure how we receive it
                            brightnessTextView.setText(receivedMessage + "%");
                            brightnessTextViewE62.setText(receivedMessage + "%");
                            brightnessTextViewE63.setText(receivedMessage + "%");
                        }
                    });

                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

        });
    }



public void openRoomActivity(){
        Intent intent=new Intent(this,RoomActivity.class);
        startActivity(intent);
        }

public void openNewRoomActivity(){
        Intent intent=new Intent(this,NewRoomActivity.class);
        startActivity(intent);
        }

@Override
protected void onPause(){
        super.onPause();
        mqttClient.disconnect();
        }

@Override
protected void onDestroy(){
        mqttClient.disconnect();
        super.onDestroy();
        }

public void temperatureNotification(){
        String temperature="36";
        int convertTemperatureToInt=Integer.parseInt(temperature);
        if(smartClassroom.getTemperature()>20){
        openTemperatureAlertBox();
        }
        }

private void openTemperatureAlertBox(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hold on!");
        builder.setMessage("Test message");
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
@Override
public void onClick(DialogInterface dialog,int which){

        }
        });
        AlertDialog tempAlert=builder.create();
        tempAlert.show();
        }

        }