// Wiring code generated from an ArduinoML model
// Application name: Dual Check Alarm

void setup(){
  pinMode(4, INPUT);  // button1 [Sensor]
  pinMode(2, INPUT);  // button2 [Sensor]
  pinMode(8, OUTPUT); // buzzer [Actuator]
}

void state_on() {
  digitalWrite(8,HIGH);
  delay(10);
  if(digitalRead(4) == LOW || digitalRead(2) == LOW ) {
    state_off();
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(8,LOW);
  delay(10);
  if(digitalRead(4) == HIGH && digitalRead(2) == HIGH ) {
    state_on();
  } else {
    state_off();
  }
}

void loop() {
  state_off();
}

