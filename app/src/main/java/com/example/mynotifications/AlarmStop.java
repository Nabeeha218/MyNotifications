package com.example.mynotifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmStop extends AppCompatActivity {

    String title;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_stop);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        textView   = (TextView)findViewById(R.id.alarm_title);
        textView.setText(title);
    }

    public void stop_alarm(View view) {
        Player.getInstance(getApplicationContext().getApplicationContext()).stopMusic();

    }
}
