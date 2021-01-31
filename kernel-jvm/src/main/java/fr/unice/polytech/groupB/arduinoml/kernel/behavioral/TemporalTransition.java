package fr.unice.polytech.groupB.arduinoml.kernel.behavioral;

import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;

import java.util.List;

public class TemporalTransition extends Transition{
    int time;


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    @Override
    public boolean isTemporal(){
        return true;
    }


}
