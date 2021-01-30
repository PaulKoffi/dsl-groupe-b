package fr.unice.polytech.groupB.arduinoml.kernel.behavioral;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.groupB.arduinoml.kernel.NamedElement;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;

public class State implements NamedElement, Visitable {

	private String name;
	private List<Action> actions = new ArrayList<Action>();
//	private Transition transition;
	private int id;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
