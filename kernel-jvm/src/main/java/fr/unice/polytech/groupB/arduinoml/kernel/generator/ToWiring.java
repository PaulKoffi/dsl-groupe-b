package fr.unice.polytech.groupB.arduinoml.kernel.generator;

import fr.unice.polytech.groupB.arduinoml.kernel.App;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
    enum PASS {ONE, TWO}
    private final static String CURRENT_STATE = "current_state";
    private int nbState = 0;
    private boolean tonality =false;
    private boolean interrupt =false;
    private int INT_MAX= 32000;
    private ArrayList<TemporalTransition> temporalTransitions = new ArrayList<>();

    public ToWiring() {
        this.result = new StringBuffer();
    }

    private void wln(String s) {
        result.append(String.format("%s\n", s));
    }

    private void w(String s) {
        result.append(String.format("%s", s));
    }

    @Override
    public void visit(App app) {
        for (Transition transition: app.getTransitions()   ) {
            if (transition.isTemporal()){
                TemporalTransition temporalTransition = (TemporalTransition) transition;
                temporalTransitions.add(temporalTransition);
            }

        }
        interrupt=app.isInterrupt();
        context.put("pass", PASS.ONE);
        wln("// Wiring code generated from an ArduinoML model");
        wln(String.format("// Application name: %s\n", app.getName()));
        this.tonality= app.getTonality();
        if (temporalTransitions.size()>0){
            wln("volatile int change =0;");
        }
        if (tonality) {
            int buzzerAlarm = 0;
            for (Brick brick : app.getBricks()) {
                if (brick.getName().equals("buzzerAlarm")) {
                    buzzerAlarm = brick.getPin();
                }
            }
            wln(String.format("int buzzerAlarm = %d;\n", buzzerAlarm));

        }
        wln("void setup(){");
        if (interrupt){
            wln("  attachInterrupt( digitalPinToInterrupt(2), gotToStateOff, FALLING );");
        }
        if (tonality){
            wln("Serial.begin(9600);");

        }
        for (Brick brick : app.getBricks()) {
            brick.accept(this);
        }
        wln("}\n");

        if (interrupt){
            wln("void gotToStateOff(){");
            wln(String.format(" change = %d;", INT_MAX));
            wln("}\n");
        }
		wln("long time = 0; long debounce = 200;\n");

        for (State state : app.getStates()) {
            state.accept(this);
            for (Transition transition : app.getTransitions()) {
                if (transition.getFrom().getName().equals(state.getName()) && !transition.isTemporal()) {
                    transition.accept(this);
                }
            }
            nbState+=1;
            wln("}\n");
        }

        //second pass, setup and loop
        context.put("pass",PASS.TWO);

        if (app.getInitial() != null)
            wln(String.format("int state = %d;", nbState));
        wln("void loop() {");

        if (!app.getStates().isEmpty()) {
            wln("  switch(state) {");
            for (State state : app.getStates()) {
                wln(String.format("    case %d:", state.getId()));
                wln(String.format("      state = state_%s();", state.getName()));
                wln("      break;");
            }
            wln("    default:");
            wln("      break;");
            wln("  }");
        }
        wln("}");
    }

    @Override
    public void visit(Actuator actuator) {
            wln(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
    }

    @Override
    public void visit(Sensor sensor) {
            wln(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
    }

    @Override
    public void visit(State state) {
        wln(String.format("int state_%s() {", state.getName()));
        if(tonality && state.isTune()){
            wln(String.format("      tone(buzzerAlarm,400,100);"));
            wln(String.format("      delay(100);" ));
            wln(String.format("      tone(buzzerAlarm,400,100);" ));
            wln(String.format("      delay(100);" ));
            wln(String.format("      tone(buzzerAlarm,400,100);" ));
            wln(String.format("      delay(100);"));
        }
        for (Action action : state.getActions()) {
            action.accept(this);
        }
        wln("  boolean guard = millis() - time > debounce;");

        if(tonality && state.isTune()){
            wln(String.format("delay(500);"));
            wln(String.format("tone(buzzerAlarm,450,500);"));
        }
        context.put(CURRENT_STATE, state);
    }

    @Override
    public void visit(Transition transition) {
        w(String.format("  if("));
        w(String.format("digitalRead(%d) == %s ", transition.getConditionActions().get(0).getSensor().getPin(), transition.getConditionActions().get(0).getValue() == SIGNAL.HIGH?"1":"0"));

        if (transition.getCondition() != null) {
            if (transition.getCondition().equals(Condition.AND)) {
                w(String.format("&& digitalRead(%d) == %s ", transition.getConditionActions().get(1).getSensor().getPin(), transition.getConditionActions().get(1).getValue() == SIGNAL.HIGH?"1":"0"));
            }
            if (transition.getCondition().equals(Condition.OR)) {
                w(String.format("|| digitalRead(%d) == %s ", transition.getConditionActions().get(1).getSensor().getPin(), transition.getConditionActions().get(1).getValue() == SIGNAL.HIGH?"1":"0"));
            }

        }

        boolean temporal =false;
        wln("&& guard) {");
        wln("    time = millis();");

        wln(String.format("    return %d;", transition.getNext().getId()));
        wln("  } else {");
        for (TemporalTransition temporalTransition:temporalTransitions ) {
            if(temporalTransition.getNext().getName().equals(transition.getNext().getName()) &&
                    temporalTransition.getFrom().getName().equals(transition.getFrom().getName())){
                temporal=true;
                temporalTransition.accept(this);
            }
        }
        if(temporal){
            wln(String.format("    return %d;", transition.getNext().getId()));
        }
        else {
            wln(String.format("    return %d;", transition.getFrom().getId()));
        }
        wln("  }");
    }

    @Override
    public void visit(Action action) {
            wln(String.format("  digitalWrite(%d,%s);", action.getActuator().getPin(), action.getValue()));
    }


    @Override
    public void visit(TemporalTransition temporalTransition){
        wln(String.format("    while( change < %d) {", temporalTransition.getTime()));
        wln("       delay(1);");
        wln("       change ++;");
        wln("    } ");
        wln("    change = 0;");
    }
}