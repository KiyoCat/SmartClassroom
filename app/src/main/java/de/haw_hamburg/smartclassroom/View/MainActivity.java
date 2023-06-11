package de.haw_hamburg.smartclassroom.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.haw_hamburg.smartclassroom.ViewModel.MqttHandler;
import de.haw_hamburg.smartclassroom.R;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static final String CLIENT_ID = "my-mqtt-client-id";
    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setAdapter(new CustomAdapter());

        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL, CLIENT_ID);

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