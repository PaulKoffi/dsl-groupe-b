package fr.unice.polytech.dsl.arduinoml.kernel.generator;

public interface Visitable {

	public void accept(Visitor visitor);

}
