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

    private Binding binding;

    public ArduinoMLModel(Binding binding) {
        this.bricks = new ArrayList<Brick>();
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
        this.binding = binding;
        this.binding.setVariable("currentState", 0);
    }

    public Tonality getTonality() {
        return tonality;
    }

    public void setTonality(Tonality tonality) {
        this.tonality = tonality;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public void createSensor(String name, Integer pinNumber) {
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensor.setPin(pinNumber);
        this.bricks.add(sensor);
        this.binding.setVariable(name, sensor);
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

    public void setInitialState(State state) {
        this.initialState = state;
    }

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
