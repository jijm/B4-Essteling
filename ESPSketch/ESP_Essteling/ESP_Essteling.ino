  int yellowButton = 34;
  int redButton = 32;
  int blueButton = 25;
  int greenButton = 27;
  int yellow = 22;
  int red = 21;
  int blue = 19;
  int green = 18;
  int sequenceSize = -1;
  int const maxSequenceSize = 20;
  int sequence[maxSequenceSize];

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


  int androidButtonPressed;
  int currentIndex;
  boolean wonGame = false;
  boolean isConnected;

 //GameStates



 enum States{
  STATE_IDLE,
  STATE_CONNECTED,
  STATE_WAITFORBUTTON,
  STATE_WAITFORANDROID,
  STATE_SHOWSEQUENCE,
  STATE_ENDGAME
  };

 States state;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  state = STATE_IDLE;
  
  pinMode(yellowButton, INPUT);
  pinMode(redButton, INPUT);
  pinMode(blueButton, INPUT);
  pinMode(greenButton, INPUT);

  pinMode(yellow, OUTPUT);
  pinMode(red, OUTPUT);
  pinMode(blue, OUTPUT);
  pinMode(green,OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
if(state == STATE_IDLE){
  //accept connection
  if(isConnected){
    state = STATE_CONNECTED;
  }
 }
if(isConnected){
  if(state == STATE_CONNECTED){
    //Establish connection
    state = STATE_WAITFORBUTTON;
    }
  
  if(state = STATE_WAITFORBUTTON){
    timeCurrent = millis();
    yellowValue = digitalRead(yellowButton);
    redValue = digitalRead(redButton);
    blueValue = digitalRead(blueButton);
    greenValue = digitalRead(greenButton);

 
  if(timeCurrent - timePrevious > 2000){
    if(yellowValue == HIGH && lastYellowValue != HIGH){
      Serial.println("Yellow added");
      digitalWrite(yellow, HIGH);
      delay(1000);
      digitalWrite(yellow, LOW);
      delay(500);

      addToSequence(yellow);
      timePrevious = timeCurrent;
    }else{
    if(redValue == HIGH && lastYellowValue != HIGH){
      Serial.println("Red added");

      digitalWrite(red, HIGH);
      delay(1000);
      digitalWrite(red, LOW);
      delay(500);

      addToSequence(red);
      timePrevious = timeCurrent;
    }else{
    if(blueValue == HIGH && lastBlueValue != HIGH){

      Serial.println("Blue added");
      digitalWrite(blue, HIGH);
      delay(1000);
      digitalWrite(blue, LOW);
      delay(500);

      addToSequence(blue);
      timePrevious = timeCurrent;

    }else{
    if(greenValue == HIGH && lastGreenValue != HIGH){

      Serial.println("Green added");
      digitalWrite(green, HIGH);
      delay(1000);
      digitalWrite(green, LOW);
      delay(500);

      addToSequence(green);
    
      timePrevious = timeCurrent;

  }}}}
}

    lastYellowValue = yellowValue;
    lastRedValue = redValue;
    lastBlueValue = blueValue;
    lastGreenValue = greenValue;
}

  if(state = STATE_SHOWSEQUENCE){
    state = STATE_WAITFORANDROID;
    Lights();
}

  if(state = STATE_WAITFORANDROID){
    if(androidButtonPressed = sequence[currentIndex]){
      if(currentIndex = sequenceSize){
        if(sequenceSize = maxSequenceSize){
          //The answer is correct and is the last sequence
          state = STATE_ENDGAME;
          wonGame = true;
        }
        else{
          //The answer is correct and a new sequence will play
        currentIndex = 0;
        state = STATE_WAITFORBUTTON;
        }
      }
      else{
        //The answer is correct and the sequence is not finished
        currentIndex += 1;
        state = STATE_WAITFORANDROID;
      }
    }
    else{
      //The answer is incorrect and the game ends
      state = STATE_ENDGAME;
      }
  }

  if(state == STATE_ENDGAME){
    //send wonGame
    //send sequenceSize?
    Reset();
  }

}
else{
  Reset();
  }
  
}

void addToSequence(int button){
  state = STATE_SHOWSEQUENCE;
    sequenceSize += 1;
    if(sequenceSize > maxSequenceSize){
      sequenceSize = 0;
    }
    sequence[sequenceSize] = button;
  }

  void Lights(){
    delay(2000);
    for(int i = 0; i <= sequenceSize; i++){
      digitalWrite(sequence[i], HIGH);
      delay(1000);
      digitalWrite(sequence[i], LOW);
      delay(100);
    }
  }

  void Reset(){
    clearSequence();
    sequenceSize = -1;
    state = STATE_IDLE;
    currentIndex = 0;
    wonGame = false;
    timeCurrent = 0;
    timePrevious = 0;
    androidButtonPressed = -100;
    lastYellowValue = LOW;
    lastRedValue = LOW;
    lastBlueValue = LOW;
    lastGreenValue = LOW;
   }

   void clearSequence(){
    for(int i = 0; i <= maxSequenceSize; i++){
      sequence[i] = -1;
    }
   }

  
