package fr.unice.polytech.dsl.arduinoml.kernel.structural;

import fr.unice.polytech.dsl.arduinoml.kernel.generator.Visitor;

public class Sensor extends Brick {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
