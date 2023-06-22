package de.haw_hamburg.smartclassroom.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.eclipse.paho.client.mqttv3.MqttException;

import de.haw_hamburg.smartclassroom.Model.MqttClient;
import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.viewmodel.MyApplication;
import de.haw_hamburg.smartclassroom.viewmodel.SmartClassroomController;

public class RoomActivity extends AppCompatActivity {
    //private RoomActivityBinding binding;
    private ImageView imageView;
    private SeekBar seekBar;
    private SmartClassroomController viewModel;
    private TextView textView;
    private MqttClient mqttClient;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);

        mqttSetup();
        initializeViews();

        viewModel = new ViewModelProvider(this).get(SmartClassroomController.class);
        viewModel.setMqttClient(mqttClient);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
                viewModel.setSeekBarValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imageView.setOnClickListener(v -> openMainActivity());
    }

    public void turnOn(View view) {
        if(aSwitch.isChecked()) {
            Toast.makeText(getApplicationContext(), "ROLLOS DOWN", Toast.LENGTH_SHORT).show();
            viewModel.setSwitchStateLiveData(true);
        } else {
            Toast.makeText(getApplicationContext(), "ROLLOS UP", Toast.LENGTH_SHORT).show();
            viewModel.setSwitchStateLiveData(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*
        if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("error", "permission for notifications not granted");
            ActivityCompat.requestPermissions(RoomActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        } else {
            showNotification(receivedMessage);
        }*/
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void mqttSetup(){
        MyApplication myApp = (MyApplication) getApplication();
        mqttClient = myApp.getMqttHandler();
        try {
            mqttClient.connect();
            mqttClient.subscribe("heater");
            mqttClient.subscribe("rollo");
        } catch (MqttException e) {
            e.printStackTrace();
            Log.e("error", "couldn't connect in RoomActivity");
        }
    }

    private void initializeViews(){
        aSwitch = (Switch) findViewById(R.id.switch2);
        seekBar = (SeekBar) findViewById(R.id.heating_seekbar);
        textView = (TextView) findViewById(R.id.heating_textview);
        imageView = (ImageView) findViewById(R.id.imageView9);
    }
}
