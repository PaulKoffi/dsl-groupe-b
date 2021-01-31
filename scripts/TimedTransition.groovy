sensor "button" pin 4
actuator "led" pin 12

tonality off

state "on" means led becomes high
state "off" means led becomes low

initial off
interrupt on


from on to off when button becomes high
from on temporalTo off after 2000 ms
from off to on when button becomes high

export "Signaling Stuff By Using Song"