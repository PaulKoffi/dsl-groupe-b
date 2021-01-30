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

    // from state1 to state2 when sensor becomes signal
    def from(State state1) {
        List<ConditionAction> conditionActionArrayList = new ArrayList<ConditionAction>()
        def closure
        [to: { state2 ->
            [when: closure = { sensor ->
                [becomes: { signal, condition ->
                    ConditionAction conditionAction = new ConditionAction()
                    c = condition
                    conditionAction.setSensor(sensor)
                    conditionAction.setValue(signal)
                    conditionActionArrayList.add(conditionAction)
                    [when: { sensor2 ->
                        [becomes: { signal2 ->
                            ConditionAction conditionAction1 = new ConditionAction()
                            conditionAction1.setSensor(sensor2)
                            conditionAction1.setValue(signal2)
                            conditionActionArrayList.add(conditionAction1)
                            ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition2(state1, state2, conditionActionArrayList, condition)
                        }]
                    }]
                }]
            }]
        }]
    }

    // initial state
    def initial(State state) {
        ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state)
    }

    // export name
    def export(String name) {
        println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }
}
