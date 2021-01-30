package fr.unice.polytech.groupB.arduinoml.dsl

import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Condition
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.ConditionAction
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.State
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Transition
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Sensor


abstract class ArduinoMLBasescript extends Script {
    // sensor "name" pin n
    def sensor(String name) {
        [pin: { n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
    }

    // actuator "name" pin n
    def actuator(String name) {
        [pin: { n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
    }

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                Action action = new Action()
                action.setActuator(actuator)
                action.setValue(signal)
                actions.add(action)
                [and: closure]
            }]
        }
        [means: closure]
    }

    // initial state
    def initial(State state) {
        ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state)
    }

    // from state1 to state2 when sensor becomes signal
    def from(State state1) {
        [to: { state2 ->
            ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2)
            [when: {
            }]
        }]
    }

    def _(Sensor sensor) {
        [is: {signal ->
            ConditionAction conditionAction = new ConditionAction()
            conditionAction.setSensor(sensor)
            conditionAction.setValue(signal)
            ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().addToLastTransition(conditionAction)
        }]
    }

    // export name
    def export(String name) {
        println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }
}
