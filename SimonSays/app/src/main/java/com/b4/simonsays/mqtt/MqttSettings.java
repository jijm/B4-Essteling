package com.b4.simonsays.mqtt;

import android.util.Log;

public class MqttSettings {
    private final String LOG_TAG = this.getClass().getName();

    private final String SERVER_ADDRESS = "maxwell.bps-software.nl";
    private final String PORT = "1883";
    private final String USERNAME = "androidTI";
    private final String PASSWORD = "&FN+g$$Qhm7j";
    private final String BASE_TOPIC = "groupb4/simonsays";
    private final int QOS = 2;

    MqttSettings() {
        Log.d(LOG_TAG, String.format("Initialized MqttSettings: %s, %s, %s, %s, %s, %s", SERVER_ADDRESS, PORT, USERNAME, PASSWORD, BASE_TOPIC, QOS));
    }

    String getFullServerAddress() {
        return String.format("tcp://%s:%s", getServerAddress(), getPort());
    }

    String getServerAddress() {
        return SERVER_ADDRESS;
    }

    String getPort() {
        return PORT;
    }

    String getUsername() {
        return USERNAME;
    }

    String getPassword() {
        return PASSWORD;
    }

    String getBaseTopic() {
        return BASE_TOPIC;
    }

    int getQos() {
        return QOS;
    }
}