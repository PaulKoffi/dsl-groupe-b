// Wiring code generated from an ArduinoML model
// Application name: Very Simple Alarm

void state_on() {
  digitalWrite(10,HIGH);
  digitalWrite(11,HIGH);
  delay(10);
  if(digitalRead(9) == LOW ) {
    state_off();
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(10,LOW);
  digitalWrite(11,LOW);
  delay(10);
  if(digitalRead(9) == HIGH ) {
    state_on();
  } else {
    state_off();
  }
}

void setup(){
  pinMode(9, INPUT);  // button [Sensor]
  pinMode(10, OUTPUT); // led [Actuator]
  pinMode(11, OUTPUT); // buzzer [Actuator]
}

void loop() {
  state_off();
}

