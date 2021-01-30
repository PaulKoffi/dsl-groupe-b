package fr.unice.polytech.groupB.arduinoml.dsl;

import java.util.*;

import fr.unice.polytech.groupB.arduinoml.kernel.App;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Brick;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Sensor;
import groovy.lang.Binding;

public class ArduinoMLModel {
	private List<Brick> bricks;
	private List<State> states;
	private List<Transition> transitions;
	private State initialState;

	private Condition currentCondition;
	private List<ConditionAction> conditionsList;

	private Binding binding;
	public ArduinoMLModel(Binding binding) {
		this.bricks = new ArrayList<Brick>();
		this.states = new ArrayList<State>();
		this.transitions = new ArrayList<Transition>();
		this.conditionsList = new ArrayList<ConditionAction>();

		this.binding = binding;
	}
	
	public void createSensor(String name, Integer pinNumber) {
		Sensor sensor = new Sensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.bricks.add(sensor);
		this.binding.setVariable(name, sensor);
//		System.out.println("> sensor " + name + " on pin " + pinNumber);
	}
	
	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new Actuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}
	
	public void createState(String name, List<Action> actions) {
		State state = new State();
		state.setName(name);
		state.setActions(actions);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}
	
	public void createTransition(State from, State to) {
		Transition transition = new Transition();
		transition.setNext(to);
		transition.setFrom(from);
		this.transitions.add(transition);
		// set CONDITION TO INITIAL MODE
		this.currentCondition = Condition.NULL;
	}

	public void addToLastTransition(ConditionAction conditionAction){
		if (this.currentCondition != Condition.NULL) {
			// on est donc en train d'interpréter la seconde partie de la condition
//			System.out.println("je suis ici");
			this.transitions.get(this.transitions.size() - 1).setCondition(this.currentCondition);
//			System.out.println("je suis ici");
		}

//		if(this.conditionsList.size()!=2){
//			this.conditionsList.add(conditionAction);
//		}else{
//			if(this.transitions.size() == 1){
//				this.transitions.get(0).setConditionActions(this.conditionsList);
//			}else {
//				this.transitions.get(this.transitions.size() - 1).setConditionActions(this.conditionsList);
//			}
//			this.conditionsList = new ArrayList<>();
//		}

		if(this.transitions.size() == 1){
			this.transitions.get(0).addToConditionActions(conditionAction);
//			System.out.println("passééééé");

		}else {
			this.transitions.get(this.transitions.size() - 1).addToConditionActions(conditionAction);
		}
	}

//	public void createTransition(State from, State to, List<ConditionAction> allCombinationsActions) {
//		Transition transition = new Transition();
//		transition.setNext(to);
//		transition.setFrom(from);
//		transition.setCondition(this.currentCondition);
//		// Range all combinations actions
//		transition.setCombinationActions(allCombinationsActions);
//		this.transitions.add(transition);
//	}

	public Condition getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(Condition currentCondition) {
		this.currentCondition = currentCondition;
	}

	public void setInitialState(State state) {
		this.initialState = state;
	}
	
	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setBricks(this.bricks);
		app.setStates(this.states);
		app.setTransitions(this.transitions);
		app.setInitial(this.initialState);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);
		return codeGenerator.getResult();
	}
}
