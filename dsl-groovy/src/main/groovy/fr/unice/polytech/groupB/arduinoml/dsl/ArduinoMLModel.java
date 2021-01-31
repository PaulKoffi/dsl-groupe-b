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
    private Tonality tonality;
    private boolean interrupt = false;

//    private State tempState1;
//    private State tempState2;


    private Condition currentCondition;
//    private Transition currentTransition;
//    private List<ConditionAction> conditionsList;

    private Binding binding;

    public ArduinoMLModel(Binding binding) {
        this.bricks = new ArrayList<Brick>();
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
//        this.conditionsList = new ArrayList<ConditionAction>();
        this.binding = binding;
        this.binding.setVariable("currentState", 0);

    }
//
//    public State getTempState1() {

    public Tonality getTonality() {
        return tonality;
    }

    public void setTonality(Tonality tonality) {
        this.tonality = tonality;
    }
//        return tempState1;
//    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

//    public void setTempState1(State tempState1) {
//        this.tempState1 = tempState1;
//    }
//
//    public State getTempState2() {
//        return tempState2;
//    }

//    public void setTempState2(State tempState2) {
//        this.tempState2 = tempState2;
//    }
//
//    public List<ConditionAction> getConditionsList() {
//        return conditionsList;
//    }

//    public void setConditionsList(List<ConditionAction> conditionsList) {
//        this.conditionsList = conditionsList;
//    }

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

    public void createState(String name, List<Action> actions, boolean tonality) {
        State state = new State();
        state.setName(name);
        state.setActions(actions);
        int id = (int) this.binding.getVariable("currentState");
        state.setTune(tonality);
        state.setId(++id);
        this.binding.setVariable("currentState", id);

        this.states.add(state);
        this.binding.setVariable(name, state);
    }

//    public void createTransition(State from, State to) {
//        Transition transition = new Transition();
//        transition.setNext(to);
//        transition.setFrom(from);
////		transition.setConditionActions(new ArrayList<>());
////		this.transitions.add(transition);
//        // set CONDITION TO INITIAL MODE
//        this.currentCondition = Condition.NULL;
//        this.currentTransition = transition;
//    }
//
    public void createTransition2(State from, State to, List<ConditionAction> conditionActions, Condition condition) {
        Transition transition = new Transition();
        transition.setNext(to);
        transition.setFrom(from);
        transition.setConditionActions(conditionActions);
        transition.setCondition(condition);
        this.transitions.add(transition);
    }

    public void createTransitionTemporal(State from, State to, int time) {
        TemporalTransition transition = new TemporalTransition();
        transition.setNext(to);
        transition.setFrom(from);
        transition.setTime(time);
        this.transitions.add(transition);
    }

//    public void addToLastTransition(ConditionAction conditionAction) {
//        if (this.currentCondition != Condition.NULL) {
//            // on est donc en train d'interpréter la seconde partie de la condition
////			System.out.println("je suis ici");
////			this.transitions.get(this.transitions.size() - 1).setCondition(this.currentCondition);
//
////			System.out.println("je suis ici");
//            this.currentTransition.getConditionActions().add(conditionAction);
//            this.currentTransition.setCondition(this.currentCondition);
//            this.transitions.add(this.currentTransition);
//
//
//        } else {
//            this.currentTransition.getConditionActions().add(conditionAction);
//        }

//		if(this.conditionsList.size()!=2){
//			this.conditionsList.add(conditionAction);
//		}else{
//			this.currentTransition.setConditionActions(this.conditionsList);
//			this.transitions.add(this.currentTransition);
////			if(this.transitions.size() == 1){
////				this.transitions.get(0).setConditionActions(this.conditionsList);
////			}else {
////				this.transitions.get(this.transitions.size() - 1).setConditionActions(this.conditionsList);
////			}
//			this.conditionsList = new ArrayList<>();
//		}
//
//        if (this.transitions.size() == 1) {
//            this.transitions.get(0).getConditionActions().add(conditionAction);
//        } else {
//            this.transitions.get(this.transitions.size() - 1).getConditionActions().add(conditionAction);
//        }
//    }

//	public void createTransition(State from, State to, List<ConditionAction> allCombinationsActions) {
//		Transition transition = new Transition();
//		transition.setNext(to);
//		transition.setFrom(from);
//		transition.setCondition(this.currentCondition);
//		// Range all combinations actions
//		transition.setCombinationActions(allCombinationsActions);
//		this.transitions.add(transition);
//	}

    public void setInitialState(State state) {
        this.initialState = state;
    }

//    public void addToConditions(ConditionAction conditionAction) {
//        this.conditionsList.add(conditionAction);
//        if (this.conditionsList.size() != 2) {
//            this.conditionsList.add(conditionAction);
//        } else {
//            Transition transition = new Transition();
//            transition.setNext(tempState1);
//            transition.setFrom(tempState2);
//            ArrayList<ConditionAction> temp = new ArrayList<>();
//            temp.add(this.conditionsList.get(0));
//            temp.add(this.conditionsList.get(1));
//            transition.setConditionActions(temp);
//            transition.setCondition(this.currentCondition);
//            this.transitions.add(transition);
//            // set CONDITION TO INITIAL MODE
//            this.currentCondition = Condition.NULL;
//            this.conditionsList = new ArrayList<>();
//        }
//    }

    @SuppressWarnings("rawtypes")
    public Object generateCode(String appName) {
        App app = new App();
        boolean t  = (tonality == Tonality.ON);
        app.setName(appName);
        app.setBricks(this.bricks);
        app.setStates(this.states);
        app.setTransitions(this.transitions);
        app.setInitial(this.initialState);
        app.setInterrupt(this.interrupt);
        app.setTonality(t);
        Visitor codeGenerator = new ToWiring();
        app.accept(codeGenerator);
        return codeGenerator.getResult();
    }
}
