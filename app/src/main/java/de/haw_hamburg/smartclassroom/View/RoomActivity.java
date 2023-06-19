package de.haw_hamburg.smartclassroom.View;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.viewmodel.MyApplication;
import de.haw_hamburg.smartclassroom.viewmodel.SmartClassroomController;

public class RoomActivity extends AppCompatActivity {
    //private RoomActivityBinding binding;
    ImageView imageView;
    SeekBar seekBar;
    Switch rollosSwitch;
    SmartClassroomController viewModel;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);

        MyApplication myApp = (MyApplication) getApplication();
        MqttClient mqttClient = myApp.getMqttHandler();
        // create ViewModel

         viewModel = new ViewModelProvider(this).get(SmartClassroomController.class);
         viewModel.setMqttClient(mqttClient);

         //rollosSwitch = (Switch) findViewById(R.id.rollosSwitch);

        /*rollosSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.rollosSwitchisClicked(isChecked);
        });*/

        seekBar = (SeekBar) findViewById(R.id.heating_seekbar);
        textView = (TextView) findViewById(R.id.heating_textview);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setSeekBarValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imageView = (ImageView) findViewById(R.id.imageView9);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("error", "permission for notifications not granted");
            ActivityCompat.requestPermissions(RoomActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        } else {
            showNotification(receivedMessage);
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void showNotification(String receivedMessage) {
        int convertTemperatureToInt = Integer.parseInt(receivedMessage);
        if (convertTemperatureToInt > 20) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(RoomActivity.this, "notif");
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

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(RoomActivity.this);
            managerCompat.notify(123, builder.build());
        }
    }

}
