package fr.unice.polytech.dsl.arduinoml.kernel.structural;

import fr.unice.polytech.dsl.arduinoml.kernel.generator.Visitor;

public class Actuator extends Brick {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
