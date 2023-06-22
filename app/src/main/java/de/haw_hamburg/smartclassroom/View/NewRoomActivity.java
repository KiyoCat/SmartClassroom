package de.haw_hamburg.smartclassroom.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import de.haw_hamburg.smartclassroom.R;
import de.haw_hamburg.smartclassroom.View.MainActivity;

public class NewRoomActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newroom_activity);

        imageView = (ImageView) findViewById(R.id.imageView9);
        imageView.setOnClickListener(v -> openMainActivity());
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
