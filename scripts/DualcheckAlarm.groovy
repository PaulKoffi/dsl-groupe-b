sensor "button1" pin 4
sensor "button2" pin 2
actuator "buzzer" pin 8

state "on" means buzzer becomes high
state "off" means buzzer becomes low

initial off

fromC on to off when button1 becomes low, or when button2 becomes low
fromC off to on when button1 becomes high, and when button2 becomes high

export "Dual Check Alarm"