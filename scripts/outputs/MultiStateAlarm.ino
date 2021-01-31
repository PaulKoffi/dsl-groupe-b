// Wiring code generated from an ArduinoML model
// Application name: Multi State Alarm

void setup(){
  pinMode(4, INPUT);  // button [Sensor]
  pinMode(8, OUTPUT); // led [Actuator]
  pinMode(12, OUTPUT); // buzzer [Actuator]
}

long time = 0; long debounce = 200;

int state_buzzerOK() {
  digitalWrite(12,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 1 && guard) {
    time = millis();
    return 2;
  } else {
    return 1;
  }
}

int state_ledOK() {
  digitalWrite(8,HIGH);
  digitalWrite(12,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 1 && guard) {
    time = millis();
    return 3;
  } else {
    return 2;
  }
}

int state_ledKO() {
  digitalWrite(8,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 1 && guard) {
    time = millis();
    return 1;
  } else {
    return 3;
  }
}

int state = 3;
void loop() {
  switch(state) {
    case 1:
      state = state_buzzerOK();
      break;
    case 2:
      state = state_ledOK();
      break;
    case 3:
      state = state_ledKO();
      break;
    default:
      break;
  }
}

