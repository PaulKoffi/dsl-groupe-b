import fr.unice.polytech.groupB.arduinoml.antlr.ModelBuilder;
import fr.unice.polytech.groupB.arduinoml.antlr.StopErrorListener;
import fr.unice.polytech.groupB.arduinoml.antlr.grammar.*;


import fr.unice.polytech.groupB.arduinoml.kernel.*;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.ToWiring;
import fr.unice.polytech.groupB.arduinoml.kernel.generator.Visitor;
import org.antlr.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main (String[] args) throws Exception {
        System.out.println("\n\nRunning the ANTLR compiler for ArduinoML");

        CharStream stream = getCharStream(args);
        App theApp = buildModel(stream);
        exportToCode(theApp);

    }

    private static CharStream getCharStream(String[] args) throws IOException {
        if (args.length < 1)
            throw new RuntimeException("no input file");
        Path input = Paths.get(new File(args[0]).toURI());
        System.out.println("Using input file: " + input);
        return CharStreams.fromPath(input);
    }

    private static App buildModel(CharStream stream) {
        ArduinomlLexer    lexer   = new ArduinomlLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new StopErrorListener());

        ArduinomlParser   parser  = new ArduinomlParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new StopErrorListener());

        ParseTreeWalker   walker  = new ParseTreeWalker();
        ModelBuilder      builder = new ModelBuilder();

        walker.walk(builder, parser.root()); // parser.root() is the entry point of the grammar

        return builder.retrieve();
    }

    private static void exportToCode(App theApp) {
        Visitor codeGenerator = new ToWiring();
        theApp.accept(codeGenerator);
        System.out.println(codeGenerator.getResult());
    }

}
