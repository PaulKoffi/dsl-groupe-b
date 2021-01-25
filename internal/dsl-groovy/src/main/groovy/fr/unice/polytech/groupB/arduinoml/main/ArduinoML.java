package fr.unice.polytech.groupB.arduinoml.main;

import java.io.File;

import fr.unice.polytech.groupB.arduinoml.dsl.ArduinoMLDSL;

public class ArduinoML {
	public static void main(String[] args) {
		ArduinoMLDSL dsl = new ArduinoMLDSL();
		if(args.length > 0) {
			dsl.eval(new File(args[0]));
		} else {
			System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
		}
	}
}
