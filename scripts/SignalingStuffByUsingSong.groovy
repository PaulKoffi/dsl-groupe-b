

sensor "button" pin 2
actuator "led" pin 3
actuator "buzzerAlarm" pin 9

tonality on

tune on state "on" means led becomes high
tune off state "off" means led becomes low

initial off

from on to off when button becomes low
from off to on when button becomes high

export "Signaling Stuff By Using Song"