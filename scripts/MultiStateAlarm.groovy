sensor "button" pin 4
actuator "led" pin 8
actuator "buzzer" pin 12

state "buzzerOK" means buzzer becomes high
state "ledOK" means led becomes high and buzzer becomes low
state "ledKO" means led becomes low

initial buzzerOK

from buzzerOK to ledOK when button becomes high
from ledOK to ledKO when button becomes high
from ledKO to buzzerOK when button becomes high

export "Multi State Alarm"