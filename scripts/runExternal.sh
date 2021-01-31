#!/bin/bash

java -jar ../dsl-antlr/target/arduinoml.dsl-antlr-1.0-jar-with-dependencies.jar $1 > outputsExternal/$2.ino
$SHELL