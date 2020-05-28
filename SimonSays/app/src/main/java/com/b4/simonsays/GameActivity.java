package com.b4.simonsays;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b4.simonsays.mqtt.MessageListener;
import com.b4.simonsays.mqtt.MqttManager;
import com.b4.simonsays.mqtt.MqttSettings;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements MessageListener {

    private final String LOG_TAG = this.getClass().getName();

    private MqttManager mqttManager;

    private enum GameStates {
        SHOWING_SEQUENCE,
        WON,
        WAITING_FOR_INPUT,
        WAITING_FOR_SEQUENCE
    }

    private GameStates gameState;

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
        mqttManager.setMessageListener(this);
    }

    private void buttonPressed(String message) {
        if (gameState.equals(GameStates.WAITING_FOR_INPUT))
            mqttManager.publishToTopic(MqttSettings.getFullAppTopic(), message, null);
    }

    @Override
    public void onMessageArrived(String topic, MqttMessage message) {

        String messageString = Arrays.toString(message.getPayload());

        switch (messageString) {
            case MqttSettings.SHOWING_SEQUENCE_MESSAGE:
            case MqttSettings.WON_MESSAGE:
            case MqttSettings.WAITING_FOR_INPUT_MESSAGE:
            case MqttSettings.WAITING_FOR_SEQUENCE_MESSAGE:
                gameState = GameStates.valueOf(messageString);
                break;

            case MqttSettings.CORRECT_MESSAGE:
                // TODO: 28/05/2020 Handle correct message
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                break;

            case MqttSettings.WRONG_MESSAGE:
                // TODO: 28/05/2020 Handle wrong message
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                break;
        }

        System.out.println(message.toString());
    }
}