sensor "button1" pin 4
sensor "button2" pin 2
actuator "buzzer" pin 8

state "on" means buzzer becomes high
state "off" means buzzer becomes low

initial off

from on to off when button1 becomes low, or when button2 becomes low, nothing

from off to on when button1 becomes high, and when button2 becomes high, nothing

export "Dual Check Alarm"