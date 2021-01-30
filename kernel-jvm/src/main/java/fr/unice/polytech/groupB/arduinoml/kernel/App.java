package fr.unice.polytech.groupB.arduinoml.kernel;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.Brick;

public class App implements NamedElement, Visitable {

	private String name;
	private List<Brick> bricks = new ArrayList<Brick>();
	private List<State> states = new ArrayList<State>();
	private List<Transition> transitions= new ArrayList<Transition>();
	private State initial;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public State getInitial() {
		return initial;
	}

	public void setInitial(State initial) {
		this.initial = initial;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}
}
