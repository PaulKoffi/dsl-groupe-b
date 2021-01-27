// Wiring code generated from an ArduinoML model
// Application name: State based Alarm

void setup(){
  pinMode(2, INPUT);  // button1 [Sensor]
  pinMode(8, OUTPUT); // led [Actuator]
}

void state_on() {
  digitalWrite(8,HIGH);
  delay(10);
  if(digitalRead(2) == HIGH ) {
    state_off();
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(8,LOW);
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

