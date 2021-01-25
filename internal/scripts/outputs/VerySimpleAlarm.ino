// Wiring code generated from an ArduinoML model
// Application name: Very Simple Alarm

void setup(){
  pinMode(9, INPUT);  // button [Sensor]
  pinMode(10, OUTPUT); // led [Actuator]
  pinMode(11, OUTPUT); // buzzer [Actuator]
}

long time = 0; long debounce = 200;

void state_on() {
  digitalWrite(10,HIGH);
  digitalWrite(11,HIGH);
  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == LOW && guard) {
    time = millis();
    state_off();
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(10,LOW);
  digitalWrite(11,LOW);
  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == HIGH && guard) {
    time = millis();
    state_on();
  } else {
    state_off();
  }
}

void loop() {
  state_off();
}

