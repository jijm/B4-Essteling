package com.b4.simonsays;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.b4.simonsays.mqtt.MqttManager;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Connect to MQTT broker:
        MqttManager.getInstance().connect(this.getApplicationContext());

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}