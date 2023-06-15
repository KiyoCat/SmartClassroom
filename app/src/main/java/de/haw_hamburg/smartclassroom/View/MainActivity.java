package de.haw_hamburg.smartclassroom.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.haw_hamburg.smartclassroom.Model.MqttHandler;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.ViewModel.MqttMessageCallback;
import de.haw_hamburg.smartclassroom.ViewModel.MyApplication;

public class MainActivity extends AppCompatActivity implements MqttMessageCallback {

    Button button;
    ImageView imageView;

    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setAdapter(new CustomAdapter());

        MyApplication myApp = (MyApplication) getApplication();
        mqttHandler = myApp.getMqttHandler();
        mqttHandler.connect();

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
        mqttHandler.subscribe("test");
        mqttHandler.publish("test", "hallo test");

    }

    public void openRoomActivity(){
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
    public void openNewRoomActivity(){
        Intent intent = new Intent(this, NewRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public String onMessageReceived(String topic, String message) {
        mqttHandler.onMessageReceived(topic, message);
        return message;
    }



    public void publishMessage(String topic, String message){
        Toast.makeText(this, "Publishing message: " + message,Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic, message);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(this, "Subscribing to topic: " + topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }
    @Override
    protected void onDestroy() {
        mqttHandler.disconnect();
        super.onDestroy();
    }




}