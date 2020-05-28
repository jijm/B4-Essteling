package com.b4.simonsays.mqtt;

import android.util.Log;

public class MqttSettings {
    private final String LOG_TAG = this.getClass().getName();

    public static final String SERVER_ADDRESS = "maxwell.bps-software.nl";
    public static final String PORT = "1883";
    public static final String USERNAME = "androidTI";
    static final String PASSWORD = "&FN+g$$Qhm7j";
    static final String BASE_TOPIC = "groupb4/simonsays";
    private static final String APP_TOPIC = "app_update";
    private static final String ESP_TOPIC = "esp_update";
    static final int QOS = 2;

    public static final String RED_BUTTON_PRESSED_MESSAGE = "RED_BUTTON_PRESSED";
    public static final String YELLOW_BUTTON_PRESSED_MESSAGE = "YELLOW_BUTTON_PRESSED";
    public static final String GREEN_BUTTON_PRESSED_MESSAGE = "GREEN_BUTTON_PRESSED";
    public static final String BLUE_BUTTON_PRESSED_MESSAGE = "BLUE_BUTTON_PRESSED";

    public static final String READY_MESSAGE = "READY";
    public static final String LEAVE_MESSAGE = "LEAVE";

    MqttSettings() {
        Log.d(LOG_TAG, String.format("Initialized MqttSettings: %s, %s, %s, %s, %s, %s", SERVER_ADDRESS, PORT, USERNAME, PASSWORD, BASE_TOPIC, QOS));
    }

    static String getFullServerAddress() {
        return String.format("tcp://%s:%s", SERVER_ADDRESS, PORT);
    }

    public static String getFullAppTopic() {
        return String.format("%s/%s", BASE_TOPIC, APP_TOPIC);
    }

    public static String getFullEspTopic() {
        return String.format("%s/%s", BASE_TOPIC, ESP_TOPIC);
    }
}