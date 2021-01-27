package fr.unice.polytech.groupB.arduinoml.kernel.structural;

import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;

public class Actuator extends Brick {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
