package fr.unice.polytech.groupB.arduinoml.dsl


import fr.unice.polytech.groupB.arduinoml.kernel.behavioral.Condition
import org.codehaus.groovy.control.CompilerConfiguration

import fr.unice.polytech.groupB.arduinoml.kernel.structural.SIGNAL

class ArduinoMLDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private ArduinoMLBinding binding
    private ArduinoMLBasescript basescript

    ArduinoMLDSL() {
        binding = new ArduinoMLBinding()
        binding.setGroovuinoMLModel(new ArduinoMLModel(binding));
        configuration = new CompilerConfiguration()
        configuration.setScriptBaseClass("fr.unice.polytech.groupB.arduinoml.dsl.ArduinoMLBasescript")
        shell = new GroovyShell(configuration)

        binding.setVariable("high", SIGNAL.HIGH)
        binding.setVariable("low", SIGNAL.LOW)
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)

        binding.setScript(script)
        script.setBinding(binding)

        script.run()
    }
}
