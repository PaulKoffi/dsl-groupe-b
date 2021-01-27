package fr.unice.polytech.groupB.arduinoml.kernel.behavioral;

import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.*;

import java.util.List;

public class Transition implements Visitable {

    private State next;
    private List<CombinationAction> combinationActions;

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

    public List<CombinationAction> getCombinationActions() {
        return combinationActions;
    }

    public void setCombinationActions(List<CombinationAction> combinationActions) {
        this.combinationActions = combinationActions;
    }
}
