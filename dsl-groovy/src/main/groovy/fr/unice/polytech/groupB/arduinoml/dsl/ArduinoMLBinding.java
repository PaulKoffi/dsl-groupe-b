package fr.unice.polytech.groupB.arduinoml.dsl;

import java.util.Map;

import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Condition;
import groovy.lang.Binding;
import groovy.lang.Script;

public class ArduinoMLBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;
	
	private ArduinoMLModel model;
	
	public ArduinoMLBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public ArduinoMLBinding(Map variables) {
		super(variables);
	}
	
	public ArduinoMLBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setGroovuinoMLModel(ArduinoMLModel model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		if ("andC".equals(name)) {
			model.setCurrentCondition(Condition.AND);
			return script;
		}
		if ("orC".equals(name)) {
			model.setCurrentCondition(Condition.OR);
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public ArduinoMLModel getGroovuinoMLModel() {
		return this.model;
	}
}
