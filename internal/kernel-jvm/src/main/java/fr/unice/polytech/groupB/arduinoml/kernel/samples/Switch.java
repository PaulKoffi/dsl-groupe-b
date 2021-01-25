package fr.unice.polytech.groupB.arduinoml.kernel.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.groupB.arduinoml.kernel.App;
import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.groupB.arduinoml.kernel.structural.*;

public class Switch {

    public static void main(String[] args) {

        // Declaring elementary bricks
//		Sensor button = new Sensor();
//		button.setName("button");
//		button.setPin(9);
//
//		Actuator led = new Actuator();
//		led.setName("LED");
//		led.setPin(12);
//
//		// Declaring states
//		State on = new State();
//		on.setName("on");
//
//		State off = new State();
//		off.setName("off");
//
//		// Creating actions
//		Action switchTheLightOn = new Action();
//		switchTheLightOn.setActuator(led);
//		switchTheLightOn.setValue(SIGNAL.HIGH);
//
//		Action switchTheLightOff = new Action();
//		switchTheLightOff.setActuator(led);
//		switchTheLightOff.setValue(SIGNAL.LOW);
//
//		// Binding actions to states
//		on.setActions(Arrays.asList(switchTheLightOn));
//		off.setActions(Arrays.asList(switchTheLightOff));
//
//		// Creating transitions
//		Transition on2off = new Transition();
//		on2off.setNext(off);
//		on2off.setSensor(button);
//		on2off.setValue(SIGNAL.HIGH);
//
//		Transition off2on = new Transition();
//		off2on.setNext(on);
//		off2on.setSensor(button);
//		off2on.setValue(SIGNAL.HIGH);
//
//		// Binding transitions to states
//		on.setTransition(on2off);
//		off.setTransition(off2on);
//
//		// Building the App
//		App theSwitch = new App();
//		theSwitch.setName("Switch!");
//		theSwitch.setBricks(Arrays.asList(button, led ));
//		theSwitch.setStates(Arrays.asList(on, off));
//		theSwitch.setInitial(off);
//
//		// Generating Code
//		Visitor codeGenerator = new ToWiring();
//		theSwitch.accept(codeGenerator);
//
//		// Printing the generated code on the console
//		System.out.println(codeGenerator.getResult());

        // Declaring elementary bricks
        Sensor button1 = new Sensor();
        button1.setName("button1");
        button1.setPin(9);

        Sensor button2 = new Sensor();
        button2.setName("button2");
        button2.setPin(10);

        Actuator led = new Actuator();
        led.setName("LED");
        led.setPin(12);

        // Declaring states
        State on = new State();
        on.setName("on");

        State off = new State();
        off.setName("off");

        // Creating actions
        Action switchTheLightOn = new Action();
        switchTheLightOn.setActuator(led);
        switchTheLightOn.setValue(SIGNAL.HIGH);

        Action switchTheLightOff = new Action();
        switchTheLightOff.setActuator(led);
        switchTheLightOff.setValue(SIGNAL.LOW);

        // Binding actions to states
        on.setActions(Arrays.asList(switchTheLightOn));
        off.setActions(Arrays.asList(switchTheLightOff));

        CombinationAction button1High = new CombinationAction();
        button1High.setSensor(button1);
        button1High.setValue(SIGNAL.HIGH);
        button1High.setCombination(Combination.AND);

        CombinationAction button2High = new CombinationAction();
        button2High.setSensor(button2);
        button2High.setValue(SIGNAL.HIGH);

        List<CombinationAction> conditionsOff2On = new ArrayList<>();
        conditionsOff2On.add(button1High);
        conditionsOff2On.add(button2High);

        CombinationAction button1Low = new CombinationAction();
        button1Low.setSensor(button1);
        button1Low.setValue(SIGNAL.LOW);
        button1Low.setCombination(Combination.OR);

        CombinationAction button2Low = new CombinationAction();
        button2Low.setSensor(button2);
        button2Low.setValue(SIGNAL.LOW);

        List<CombinationAction> conditionsOn2Off = new ArrayList<>();
        conditionsOn2Off.add(button1Low);
        conditionsOn2Off.add(button2Low);

        // Creating transitions
        Transition on2off = new Transition();
        on2off.setNext(off);
        on2off.setCombinationActions(conditionsOn2Off);

        Transition off2on = new Transition();
        off2on.setNext(on);
        off2on.setCombinationActions(conditionsOff2On);

        // Binding transitions to states
        on.setTransition(on2off);
        off.setTransition(off2on);

        // Building the App
        App theSwitch = new App();
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(button1, led));
        theSwitch.setStates(Arrays.asList(on, off));
        theSwitch.setInitial(off);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
