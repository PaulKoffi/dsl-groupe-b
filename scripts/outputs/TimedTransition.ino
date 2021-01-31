// Wiring code generated from an ArduinoML model
// Application name: Signaling Stuff By Using Song

volatile int change =0;
void setup(){
   attachInterrupt( digitalPinToInterrupt(2), gotToStateOff, FALLING );
  pinMode(4, INPUT);  // button [Sensor]
  pinMode(12, OUTPUT); // led [Actuator]
}

void gotToStateOff(){

   change = 32000;

}

long time = 0; long debounce = 200;

int state_on() {
  digitalWrite(12,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 1 && guard) {
    time = millis();
    return 2;
  } else {
    while( change < 2000) {
       delay(1);
       change ++;
    } 
    change = 0;
    return 2;
  }
}

int state_off() {
  digitalWrite(12,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 1 && guard) {
    time = millis();
    return 1;
  } else {
    return 2;
  }
}

int state = 2;
void loop() {
  switch(state) {
    case 1:
      state = state_on();
      break;
    case 2:
      state = state_off();
      break;
    default:
      break;
  }
}

