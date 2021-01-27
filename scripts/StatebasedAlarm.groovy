sensor "button1" pin 2
actuator "led" pin 8

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when button1 becomes high, nothing
from off to on when button1 becomes high, nothing

export "State based Alarm"