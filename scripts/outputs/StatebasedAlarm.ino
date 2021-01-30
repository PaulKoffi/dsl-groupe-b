// Wiring code generated from an ArduinoML model
// Application name: State based Alarm

void setup(){
  pinMode(2, INPUT);  // button1 [Sensor]
  pinMode(8, OUTPUT); // led [Actuator]
}

long time = 0; long debounce = 200;

int state_on() {
  digitalWrite(8,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(2) == 1 && guard) {
    time = millis();
    return 2;
  } else {
    return 1;
  }
}

int state_off() {
  digitalWrite(8,LOW);
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

