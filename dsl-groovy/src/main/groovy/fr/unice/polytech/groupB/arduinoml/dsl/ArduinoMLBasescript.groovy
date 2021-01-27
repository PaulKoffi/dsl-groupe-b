package fr.unice.polytech.groupB.arduinoml.dsl

import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.CombinationAction
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.State


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
//		[to: { state2 ->
//			[when: { sensor ->
//				[becomes: { signal ->
//					((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, sensor, signal)
//				}]
//			}]
//		}]

        List<CombinationAction> combinationActions = new ArrayList<CombinationAction>()

        def closure
        [to: { state2 ->
            ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1, state2, combinationActions)
            [when: closure = { sensor ->
                [becomes: { signal, combination ->
                    CombinationAction combinationAction = new CombinationAction()
                    combinationAction.setSensor(sensor)
                    combinationAction.setValue(signal)
                    combinationAction.setCombination(combination)
                    combinationActions.add(combinationAction)
                    [when: closure]
                }]
            }]
        }]
    }

    // export name
    def export(String name) {
        println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }
}
