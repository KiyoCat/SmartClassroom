package de.haw_hamburg.smartclassroom.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.MqttException;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.ViewModel.MyApplication;

public class MainActivity extends AppCompatActivity{

    Button button;
    ImageView imageView;

    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setAdapter(new CustomAdapter());

        MyApplication myApp = (MyApplication) getApplication();
        mqttClient = myApp.getMqttHandler();
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        button = (Button) findViewById(R.id.roomActivity_Button);
        button.setOnClickListener(new View.OnClickListener(){
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

        // testing, ignore pls
        mqttClient.subscribe("test");
        mqttClient.publish("test", "hallo test");

    }

    public void openRoomActivity(){
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
    public void openNewRoomActivity(){
        Intent intent = new Intent(this, NewRoomActivity.class);
        startActivity(intent);
    }

    public void publishMessage(String topic, String message){
        Toast.makeText(this, "Publishing message: " + message,Toast.LENGTH_SHORT).show();
        mqttClient.publish(topic, message);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(this, "Subscribing to topic: " + topic, Toast.LENGTH_SHORT).show();
        mqttClient.subscribe(topic);
    }
    @Override
    protected void onDestroy() {
        mqttClient.disconnect();
        super.onDestroy();
    }




}