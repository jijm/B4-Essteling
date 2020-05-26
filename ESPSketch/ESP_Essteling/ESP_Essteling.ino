  int yellowButton = 34;
  int redButton = 32;
  int blueButton = 25;
  int greenButton = 27;
  int yellow = 0;
  int red = 1;
  int blue = 2;
  int green = 3;
  int sequence[100];
  int sequenceSize = -1;

  int lastYellowValue = LOW;
  int lastRedValue = LOW;
  int lastBlueValue = LOW;
  int lastGreenValue = LOW;

  int yellowValue = 0;
  int redValue = 0;
  int blueValue = 0;
  int greenValue = 0;
  long timeCurrent = 0;
  long timePrevious = 0; 

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(yellowButton, INPUT);
  pinMode(redButton, INPUT);
  pinMode(blueButton, INPUT);
  pinMode(greenButton, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:

  timeCurrent = millis();
  yellowValue = digitalRead(yellowButton);
  redValue = digitalRead(redButton);
  blueValue = digitalRead(blueButton);
  greenValue = digitalRead(greenButton);

 
if(timeCurrent - timePrevious > 1000){
  if(yellowValue == HIGH && lastYellowValue != HIGH){
    Serial.println("Yellow added");
    addToSequence(yellow);
    timePrevious = timeCurrent;
  }else{
  if(redValue == HIGH && lastYellowValue != HIGH){
    Serial.println("Red added");
    addToSequence(red);
    timePrevious = timeCurrent;
  }else{
  if(blueValue == HIGH && lastBlueValue != HIGH){
    addToSequence(blue);
    Serial.println("Blue added");
        timePrevious = timeCurrent;

  }else{
  if(greenValue == HIGH && lastGreenValue != HIGH){
    addToSequence(green);
    Serial.println("Green added");
        timePrevious = timeCurrent;

  }
   }
  }
  }
}

  lastYellowValue = yellowValue;
  lastRedValue = redValue;
  lastBlueValue = blueValue;
  lastGreenValue = greenValue;
  
  /*
if(yellowValue == HIGH){
  Serial.println("Yellow Button pressed!");
}
if(redValue == HIGH){
  Serial.println("Red Button pressed!");
}
if(blueValue == HIGH){
  Serial.println("Blue button pressed!");
}
if(greenValue == HIGH){
  Serial.println("Green Button pressed!");
}
*/
  

}

void addToSequence(int button){
    sequenceSize += 1;
    if(sequenceSize > 100){
      sequenceSize = 0;
    }
    sequence[sequenceSize] = button;
    Serial.println("The sequence:");
    for(int i = 0; i <= sequenceSize; i++){
      Serial.print(sequence[i]);
    }
    Serial.println();
  }
