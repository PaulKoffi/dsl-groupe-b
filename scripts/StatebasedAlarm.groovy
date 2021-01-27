sensor "button1" pin 9
actuator "led" pin 10

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when button1 becomes high, nothing
from off to on when button1 becomes high, nothing

export "State based Alarm"