sensor "button" pin 2
actuator "led" pin 11
actuator "buzzer" pin 13

state "on" means led becomes high and buzzer becomes high
state "off" means led becomes low  and buzzer becomes low

initial off

from on to off when button becomes low
from off to on when button becomes high

export "Very Simple Alarm"