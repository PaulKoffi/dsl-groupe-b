package fr.unice.polytech.groupB.arduinoml.kernel.behavioral;

import fr.unice.polytech.groupB.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Sensor;

public class    CombinationAction {
    private Sensor sensor;
    private SIGNAL value;
    private Combination combination;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public SIGNAL getValue() {
        return value;
    }

    public void setValue(SIGNAL value) {
        this.value = value;
    }

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }
}
