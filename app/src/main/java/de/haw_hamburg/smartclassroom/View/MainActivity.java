package de.haw_hamburg.smartclassroom.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.viewmodel.MyApplication;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private TextView temperatureTextView;
    private TextView brightnessTextView;
    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttSetUp();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notif", "Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            Log.d("test", "first build");
        }

        initializeViews();
        setClickListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mqttClient.getClient().setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @SuppressLint("SetTextI18n")
            @Override
            public void messageArrived(String topic, MqttMessage message) {

                if (topic.equals("temperature")) {
                    String receivedMessage = new String(message.getPayload(), StandardCharsets.UTF_8);

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("error", "permission for notifications not granted");
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                    } else {
                        showNotification(receivedMessage);
                    }


                    runOnUiThread(() -> temperatureTextView.setText(receivedMessage + "°C"));
                } else if (topic.equals("brightness")) {
                    String receivedMessage = new String(message.getPayload(), StandardCharsets.UTF_8);
                    runOnUiThread(() -> brightnessTextView.setText(receivedMessage + "%"));

                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

        });
    }

    public void openRoomActivity() {
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }

    public void openNewRoomActivity() {
        Intent intent = new Intent(this, NewRoomActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mqttClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        mqttClient.disconnect();
        super.onDestroy();
    }

    private void showNotification(String receivedMessage) {
        int convertTemperatureToInt = Integer.parseInt(receivedMessage);
        if (convertTemperatureToInt > 20) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "notif");
            builder.setContentTitle("Smart Classroom");
            builder.setContentText("It's warm outside, turn down the heating!");
            builder.setSmallIcon(R.drawable.notif_bell);
            builder.setAutoCancel(true);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel("notif", "Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("General Notifications");
                channel.enableLights(true);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
            }

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            managerCompat.notify(123, builder.build());
        }
    }
    private void mqttSetUp(){
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
    }

    private void initializeViews(){
        button = (Button) findViewById(R.id.roomActivity_Button);
        imageView = (ImageView) findViewById(R.id.newRoom_ImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureCelsius_MaterialTextView);
        brightnessTextView = (TextView) findViewById(R.id.lightPercentage_MaterialTextView);
    }

    private void setClickListeners(){
        button.setOnClickListener(v -> openRoomActivity());
        imageView.setOnClickListener(v -> openNewRoomActivity());
    }
}