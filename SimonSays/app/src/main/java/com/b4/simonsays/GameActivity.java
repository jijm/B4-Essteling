package com.b4.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private final static String RED_BUTTON_PRESS = "red_button";
    private final static String YELLOW_BUTTON_PRESS = "yellow_button";
    private final static String GREEN_BUTTON_PRESS = "green_button";
    private final static String BLUE_BUTTON_PRESS = "blue_button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button redButton = findViewById(R.id.redButton);
        Button yellowButton = findViewById(R.id.yellowButton);
        Button greenButton = findViewById(R.id.greenButton);
        Button blueButton = findViewById(R.id.blueButton);

        redButton.setOnClickListener(this::onClickRedButton);
        yellowButton.setOnClickListener(this::onClickYellowButton);
        greenButton.setOnClickListener(this::onClickGreenButton);
        blueButton.setOnClickListener(this::onClickBlueButton);
    }

    private void onClickRedButton(View view) {
        // TODO: 26/05/2020 Send message to MQTT: red button was pressed
        Toast.makeText(this, "Red button was pressed", Toast.LENGTH_LONG).show();
    }

    private void onClickYellowButton(View view) {
        // TODO: 26/05/2020 Send message to MQTT: yellow button was pressed
        Toast.makeText(this, "Yellow button was pressed", Toast.LENGTH_LONG).show();
    }

    private void onClickGreenButton(View view) {
        // TODO: 26/05/2020 Send message to MQTT: green button was pressed
        Toast.makeText(this, "Green button was pressed", Toast.LENGTH_LONG).show();
    }

    private void onClickBlueButton(View view) {
        // TODO: 26/05/2020 Send message to MQTT: blue button was pressed
        Toast.makeText(this, "Blue button was pressed", Toast.LENGTH_LONG).show();
    }

}