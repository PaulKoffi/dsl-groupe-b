// Wiring code generated from an ArduinoML model
// Application name: Signaling Stuff By Using Song

int buzzerAlarm = 9;

void setup(){
Serial.begin(9600);
  pinMode(2, INPUT);  // button [Sensor]
  pinMode(3, OUTPUT); // led [Actuator]
  pinMode(9, OUTPUT); // buzzerAlarm [Actuator]
}

long time = 0; long debounce = 200;

int state_on() {
      tone(buzzerAlarm,400,100);
      delay(100);
      tone(buzzerAlarm,400,100);
      delay(100);
      tone(buzzerAlarm,400,100);
      delay(100);
  digitalWrite(3,HIGH);
  boolean guard = millis() - time > debounce;
delay(500);
tone(buzzerAlarm,450,500);
  if(digitalRead(2) == 0 && guard) {
    time = millis();
    return 2;
  } else {
    return 1;
  }
}

int state_off() {
  digitalWrite(3,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(2) == 1 && guard) {
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

