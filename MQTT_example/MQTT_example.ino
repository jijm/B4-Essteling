/*
 * MQTT example
 * Avans Hogeschool Breda
 * 
 * Deze Arduino sketch is bedoeld voor een ESP32 development board
 * (zoals DOIT ESP32 DEVKIT V1)
 * Sluit een via I2C aangesloten LCD (2x16 chars) aan op pin 22 en 21
 * SCL = pin 22
 * SDA = pin 21
 * Sluit 2 drukknoppen (schakelend naar GND) aan op pin 5 en 4
 * Sluit een actieve buzzer aan op pin 23
 * 
 * De sketch gebruikt de pubsubclient voor MQTT van Nick O'Leary
 * https://github.com/knolleary/pubsubclient
 * 
 */

// Gebruikte bibliotheken
#include <WiFi.h>
#include <PubSubClient.h>
#include <LiquidCrystal_I2C.h>

// Zelf instellen voor je eigen WLAN
const char* WLAN_SSID = "Ziggo8399580";
const char* WLAN_ACCESS_KEY = "PdB08091967";

// CLIENT_ID moet uniek zijn, dus zelf aanpassen (willekeurige letters en cijfers)
const char* MQTT_CLIENT_ID = "2151638";
// Gegevens van de MQTT broker die we in TI-1.4 kunnen gebruiken
const char* MQTT_BROKER_URL = "maxwell.bps-software.nl";
const int   MQTT_PORT = 1883;
const char* MQTT_USERNAME = "androidTI";
const char* MQTT_PASSWORD = "&FN+g$$Qhm7j";

// Definieer de MQTT topics
const char* MQTT_TOPIC_LCD = "Demo/Arne/LcdTxt";
const char* MQTT_TOPIC_BUTTON1 = "Demo/Arne/Btn1";
const char* MQTT_TOPIC_BUTTON2 = "Demo/Arne/Btn2";
// Definieer de te gebruiken Quality of Service (QoS)
const int   MQTT_QOS = 0;

// Sluit de buttons en buzzer op deze pinnen aan
const int BUTTON1_PIN = 5;
const int BUTTON2_PIN = 4;
const int BUZZER_PIN = 23;

// Lengte van een regel tekst op het LCD
const int LINE_LENGTH = 16;

WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);
LiquidCrystal_I2C lcd(0x27, 16, 2);

#define LED_BUILTIN 2

bool button1PressedPrev = false; // Was de knop bij de vorige scan ingedrukt?
bool button2PressedPrev = false;
bool ledIsOn = true; // Voor de ingebouwde LED, soort keep-alive knipperlicht

/*
 * De MQTT client callback die wordt aangeroepen bij elk bericht dat
 * we van de MQTT broker ontvangen
 */
void mqttCallback(char* topic, byte* payload, unsigned int length) {
  // Logging
  Serial.print("MQTT callback called for topic ");
  Serial.println(topic);
  Serial.print("Payload length ");
  Serial.println(length);
  // Genereer een korte piep
  digitalWrite(BUZZER_PIN, HIGH);
  delay(50);
  digitalWrite(BUZZER_PIN, LOW);
  // Kijk welk topic is ontvangen en handel daarnaar
  if (strcmp(topic, MQTT_TOPIC_LCD) == 0) {
    // De payload is een tekst voor op het LCD
    // Let op, geen null-terminated string, dus voeg zelf de \0 toe
    char txt[LINE_LENGTH + 1];
    for (int i = 0; i < LINE_LENGTH + 1; i++) { txt[i] = '\0'; }
    strncpy(txt, (const char*) payload, length > 16 ? 16 : length);
    // Laat de tekst zien in zowel log als op het LCD
    Serial.print("Text: ");
    Serial.println(txt);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(MQTT_TOPIC_LCD);
    lcd.setCursor(0, 1);
    lcd.print(txt);
  }
}

void setup() {
  // Ingebouwde LED wordt als een soort keep-alive knipperLED gebruikt
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, ledIsOn);
  // Open de verbinding naar de seriële terminal
  Serial.begin(115200);
  Serial.println("ESP32 MQTT example");
  // Zet de LCD op
  lcd.begin();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print(MQTT_TOPIC_LCD);
  // Zet de actieve buzzer op
  pinMode(BUZZER_PIN, OUTPUT);
  digitalWrite(BUZZER_PIN, LOW);
  // Zet de WiFi verbinding op
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  Serial.println("Connecting to ");
  Serial.println(WLAN_SSID);
  WiFi.begin(WLAN_SSID, WLAN_ACCESS_KEY);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // Zet de MQTT client op
  mqttClient.setServer(MQTT_BROKER_URL, MQTT_PORT);
  mqttClient.setCallback(mqttCallback);
  // Maak verbinding met de MQTT broker
  if (!mqttClient.connect(MQTT_CLIENT_ID, MQTT_USERNAME, MQTT_PASSWORD)) {
    Serial.println("Failed to connect to MQTT broker");
  } else {
    Serial.println("Connected to MQTT broker");
  }
  // Subscribe op de LCD topic
  if (!mqttClient.subscribe(MQTT_TOPIC_LCD, MQTT_QOS)) {
    Serial.print("Failed to subscribe to topic ");
    Serial.println(MQTT_TOPIC_LCD);
  } else {
    Serial.print("Subscribed to topic ");
    Serial.println(MQTT_TOPIC_LCD);
  }
}

void loop() {
  // Nodig om de MQTT zijn werk te laten doen
  mqttClient.loop();
  // Controleer of een knop is ingedrukt en handel dat af
  bool button1PressedNow = !digitalRead(BUTTON1_PIN);
  if (button1PressedNow && !button1PressedPrev) {
    // Handel knopdruk af
    Serial.println("Knop 1 ingedrukt");
    mqttClient.publish(MQTT_TOPIC_BUTTON1, "Pressed");
    mqttClient.publish("student/timo/LcdTxt", "Hey Timo gr A");
  }
  button1PressedPrev = button1PressedNow;
  bool button2PressedNow = !digitalRead(BUTTON2_PIN);
  if (button2PressedNow && !button2PressedPrev) {
    // Handel knopdruk af
    Serial.println("Knop 2 ingedrukt");
    mqttClient.publish(MQTT_TOPIC_BUTTON2, "Pressed");
  }
  button2PressedPrev = button2PressedNow;
  // Laat de ingebouwde LED knipperen
  ledIsOn = !ledIsOn;
  digitalWrite(LED_BUILTIN, ledIsOn);
  delay(100);
}
