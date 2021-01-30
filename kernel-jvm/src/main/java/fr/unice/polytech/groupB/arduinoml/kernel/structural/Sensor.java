package fr.unice.polytech.groupB.arduinoml.kernel.structural;

import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;

public class Sensor extends Brick {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
