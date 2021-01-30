package fr.unice.polytech.groupB.arduinoml.kernel.behavioral;

import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Transition implements Visitable {

    private State next;
    private State from;
    private List<ConditionAction> conditionActions = new ArrayList<ConditionAction>();
    private Condition condition = Condition.NULL;

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public List<ConditionAction> getConditionActions() {
        return conditionActions;
    }

    public void setConditionActions(List<ConditionAction> conditionActions) {
        this.conditionActions = conditionActions;
    }

    public void addToConditionActions(ConditionAction conditionActions) {
        this.conditionActions.add(conditionActions);
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
