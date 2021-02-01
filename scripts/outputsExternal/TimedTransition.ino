// Wiring code generated from an ArduinoML model
// Application name: "Very Simple Alarm"

void setup(){
  pinMode(4, INPUT);  // button [Sensor]
  pinMode(8, OUTPUT); // led [Actuator]
  pinMode(12, OUTPUT); // buzzer [Actuator]
}

long time = 0; long debounce = 200;

int state_on() {
  digitalWrite(8,HIGH);
  digitalWrite(12,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(4) == 0 && guard) {
    time = millis();
    return 2;
  } else {
    return 1;
  }
}

int state_off() {
  digitalWrite(8,LOW);
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

