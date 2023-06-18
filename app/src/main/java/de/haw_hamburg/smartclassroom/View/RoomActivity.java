package de.haw_hamburg.smartclassroom.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.viewmodel.SmartClassroomController;
import de.haw_hamburg.smartclassroom.databinding.RoomActivityBinding;

public class RoomActivity extends AppCompatActivity {
    private RoomActivityBinding binding;
    ImageView imageView;
    SeekBar seekBar;
    Switch rollosSwitch;
    SmartClassroomController viewModel;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);

        binding = DataBindingUtil.setContentView(this, R.layout.room_activity);
        viewModel = new ViewModelProvider(this).get(SmartClassroomController.class);
        binding.setSmartClassroomController(viewModel);

        rollosSwitch = (Switch) findViewById(R.id.rollosSwitch);

        rollosSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.rollosSwitchisClicked(isChecked);
        });

        seekBar = (SeekBar) findViewById(R.id.heating_seekbar);
        textView = (TextView) findViewById(R.id.heating_textview);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setSeekBarValue(progress);
               // viewModel.sendHeaterValueToServer(progress);
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

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
