sensor "button" pin 9
actuator "led" pin 10
actuator "buzzer" pin 11

state "buzzerOK" means buzzer becomes high
state "ledOK" means led becomes high and buzzer becomes low
state "ledKO" means led becomes low

initial buzzerOK

from buzzerOK to ledOK when button becomes high, nothing
from ledOK to ledKO when button becomes high, nothing
from ledKO to buzzerOK when button becomes high, nothing

export "Multi State Alarm"