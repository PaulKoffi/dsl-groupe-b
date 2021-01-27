// Wiring code generated from an ArduinoML model
// Application name: Very Simple Alarm

void setup(){
  pinMode(2, INPUT);  // button [Sensor]
  pinMode(13, OUTPUT); // led [Actuator]
  pinMode(11, OUTPUT); // buzzer [Actuator]
}

void state_on() {
  digitalWrite(13,HIGH);
  digitalWrite(11,HIGH);
  delay(10);
  if(digitalRead(2) == LOW ) {
    state_off();
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(13,LOW);
  digitalWrite(11,LOW);
  delay(10);
  if(digitalRead(2) == HIGH ) {
    state_on();
  } else {
    state_off();
  }
}

void loop() {
  state_off();
}
