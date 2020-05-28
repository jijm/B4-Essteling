package com.b4.simonsays.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MessageListener {
    void onMessageArrived(String topic, MqttMessage message);
}