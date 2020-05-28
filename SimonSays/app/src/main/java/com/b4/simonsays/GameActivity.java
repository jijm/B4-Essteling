package com.b4.simonsays;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.b4.simonsays.mqtt.MqttManager;
import com.b4.simonsays.mqtt.MqttSettings;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class GameActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getName();

    private MqttManager mqttManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button redButton = findViewById(R.id.redButton);
        Button yellowButton = findViewById(R.id.yellowButton);
        Button greenButton = findViewById(R.id.greenButton);
        Button blueButton = findViewById(R.id.blueButton);

        redButton.setOnClickListener(e -> buttonPressed(MqttSettings.RED_BUTTON_PRESSED_MESSAGE));
        yellowButton.setOnClickListener(e -> buttonPressed(MqttSettings.YELLOW_BUTTON_PRESSED_MESSAGE));
        greenButton.setOnClickListener(e -> buttonPressed(MqttSettings.GREEN_BUTTON_PRESSED_MESSAGE));
        blueButton.setOnClickListener(e -> buttonPressed(MqttSettings.BLUE_BUTTON_PRESSED_MESSAGE));

        mqttManager = MqttManager.getInstance();
        mqttManager.subscribeToTopic(MqttSettings.getFullEspTopic(), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d(LOG_TAG, "Successfully subscribed to topic!");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.e(LOG_TAG, "Failed to subscribe to topic!");
            }
        });
    }

    private void buttonPressed(String message) {
        mqttManager.publishToTopic(MqttSettings.getFullAppTopic(), message, null);
    }
}