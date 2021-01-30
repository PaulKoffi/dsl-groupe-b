package fr.unice.polytech.groupB.arduinoml.kernel.generator;

import fr.unice.polytech.groupB.arduinoml.kernel.App;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.*;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

    private final static String CURRENT_STATE = "current_state";

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
        wln("// Wiring code generated from an ArduinoML model");
        wln(String.format("// Application name: %s\n", app.getName()));

        wln("void setup(){");
        for (Brick brick : app.getBricks()) {
            brick.accept(this);
        }
        wln("}\n");

//		wln("long time = 0; long debounce = 200;\n");


        for (State state : app.getStates()) {
            state.accept(this);
            for (Transition transition : app.getTransitions()) {
                if (transition.getFrom().getName().equals(state.getName())) {
                    transition.accept(this);
                }
            }
        }

        wln("void loop() {");
        wln(String.format("  state_%s();", app.getInitial().getName()));
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
        System.out.println(state.getName());
//		System.out.println(state.getTransition());
        wln(String.format("void state_%s() {", state.getName()));
        for (Action action : state.getActions()) {
            action.accept(this);
        }
//		wln("  boolean guard = millis() - time > debounce;");
        wln("  delay(10);");

        context.put(CURRENT_STATE, state);

//		state.getTransition().accept(this);
        wln("}\n");
    }

    @Override
    public void visit(Transition transition) {
        w(String.format("  if("));
        w(String.format("digitalRead(%d) == %s ", transition.getConditionActions().get(0).getSensor().getPin(), transition.getConditionActions().get(0).getValue()));

        if (transition.getCondition() != null) {
            if (transition.getCondition().equals(Condition.AND)) {
                w(String.format("&& "));
            }
            if (transition.getCondition().equals(Condition.OR)) {
                w(String.format("|| "));
            }
            w(String.format("digitalRead(%d) == %s ", transition.getConditionActions().get(1).getSensor().getPin(), transition.getConditionActions().get(1).getValue()));
        }

        wln(String.format(") {"));

//		wln(String.format("&& guard) {"));
//		wln("    time = millis();");
        wln(String.format("    state_%s();", transition.getNext().getName()));
        wln("  } else {");
        wln(String.format("    state_%s();", ((State) context.get(CURRENT_STATE)).getName()));
        wln("  }");
    }

    @Override
    public void visit(Action action) {
        wln(String.format("  digitalWrite(%d,%s);", action.getActuator().getPin(), action.getValue()));
    }

}