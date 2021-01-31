// Wiring code generated from an ArduinoML model
// Application name: Very Simple Alarm

long time = 0; long debounce = 200;

int state_on() {
  digitalWrite(10,HIGH);
  digitalWrite(11,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == 0 && guard) {
    time = millis();
    return 2;
  } else {
    return 1;
  }
}

int state_off() {
  digitalWrite(10,LOW);
  digitalWrite(11,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == 1 && guard) {
    time = millis();
    return 1;
  } else {
    return 2;
  }
}

void setup(){
  pinMode(9, INPUT);  // button [Sensor]
  pinMode(10, OUTPUT); // led [Actuator]
  pinMode(11, OUTPUT); // buzzer [Actuator]
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

