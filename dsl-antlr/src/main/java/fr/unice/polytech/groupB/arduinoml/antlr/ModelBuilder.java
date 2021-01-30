package fr.unice.polytech.groupB.arduinoml.antlr;

import fr.unice.polytech.groupB.arduinoml.antlr.grammar.*;


import fr.unice.polytech.groupB.arduinoml.kernel.App;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.ConditionAction;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Sensor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelBuilder extends ArduinomlBaseListener {

    /********************
     ** Business Logic **
     ********************/

    private App theApp = null;
    private boolean built = false;

    public App retrieve() {
        if (built) { return theApp; }
        throw new RuntimeException("Cannot retrieve a model that was not created!");
    }

    /*******************
     ** Symbol tables **
     *******************/


    private Map<String, Sensor>   sensors   = new HashMap<>();
    private Map<String, Actuator> actuators = new HashMap<>();
    private Map<String, State>    states  = new HashMap<>();
    private Map<String, Binding>  bindings  = new HashMap<>();

    private class Binding { // used to support state resolution for transitions
        private State to;
        private State from;
        private List<ConditionAction> conditionActions;
    }

    private final int avoidSpecials =1;
    private State currentState = null;
    private Binding currentBinding =null;

    /**************************
     ** Listening mechanisms **
     **************************/

    @Override
    public void enterRoot(ArduinomlParser.RootContext ctx) {
        built = false;
        theApp = new App();
    }

    @Override public void exitRoot(ArduinomlParser.RootContext ctx) {
        // Resolving states in transitions
        bindings.forEach((key, binding) ->  {
            Transition t = new Transition();
            t.setNext(states.get(binding.to));
            });
        this.built = true;
    }

    @Override
    public void enterDeclaration(ArduinomlParser.DeclarationContext ctx) {
        theApp.setName(ctx.name.getText());
    }

    @Override
    public void enterSensor(ArduinomlParser.SensorContext ctx) {
        Sensor sensor = new Sensor();
        sensor.setName(ctx.location().id.getText().substring(avoidSpecials,ctx.location().id.getText().length()-avoidSpecials));
        sensor.setPin(Integer.parseInt(ctx.location().port.getText()));
        this.theApp.getBricks().add(sensor);
        sensors.put(sensor.getName(), sensor);
    }

    @Override
    public void enterActuator(ArduinomlParser.ActuatorContext ctx) {
        Actuator actuator = new Actuator();
        actuator.setName(ctx.location().id.getText().substring(avoidSpecials,ctx.location().id.getText().length()-avoidSpecials));
        actuator.setPin(Integer.parseInt(ctx.location().port.getText()));
        this.theApp.getBricks().add(actuator);
        actuators.put(actuator.getName(), actuator);
    }

    @Override
    public void enterState(ArduinomlParser.StateContext ctx) {
        State local = new State();
        local.setName(ctx.name.getText().substring(avoidSpecials,ctx.name.getText().length()-avoidSpecials));
        this.currentState = local;
        this.states.put(local.getName(), local);
    }

    @Override
    public void exitState(ArduinomlParser.StateContext ctx) {
        this.theApp.getStates().add(this.currentState);
        this.currentState = null;

    }

    @Override
    public void enterAction(ArduinomlParser.ActionContext ctx) {
        Action action = new Action();
        action.setActuator(actuators.get(ctx.receiver.getText()));
        action.setValue(SIGNAL.valueOf(ctx.value.getText().toUpperCase()));
        currentState.getActions().add(action);
    }

    @Override
    public void enterTransition(ArduinomlParser.TransitionContext ctx) {
         //Creating a placeholder as the next state might not have been compiled yet.
        Binding toBeResolvedLater = new Binding();
        this.currentBinding = toBeResolvedLater;

        toBeResolvedLater.to = states.get(ctx.end.getText());
        toBeResolvedLater.from = states.get(ctx.begin.getText());
        this.currentBinding = toBeResolvedLater;
        this.currentBinding.conditionActions = new ArrayList<>();


    }

    @Override
    public void exitTransition(ArduinomlParser.TransitionContext ctx) {
        Transition transition = new Transition();
        transition.setNext(currentBinding.to);
        transition.setFrom(currentBinding.from);
        transition.setConditionActions(currentBinding.conditionActions);
        bindings.put(ctx.begin.getText(), currentBinding);
        theApp.getTransitions().add(transition);
        this.currentBinding = null;

    }

    @Override
    public void enterCombinationAction(ArduinomlParser.CombinationActionContext ctx){
        ConditionAction conditionAction =new ConditionAction();
        conditionAction.setSensor(sensors.get(ctx.source.getText()));
        conditionAction.setValue(SIGNAL.valueOf(ctx.value.getText().toUpperCase()));
        currentBinding.conditionActions.add(conditionAction);
    }

    @Override
    public void enterInitial(ArduinomlParser.InitialContext ctx) {
        this.theApp.setInitial(states.get(ctx.starting.getText()));
    }

}

