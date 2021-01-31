sensor "button" pin 4
actuator "led" pin 8
actuator "buzzer" pin 12

state "on" means led becomes high and buzzer becomes high
state "off" means led becomes low  and buzzer becomes low

initial off

from on to off when button becomes low
from off to on when button becomes high

export "Very Simple Alarm"