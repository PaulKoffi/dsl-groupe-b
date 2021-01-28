grammar Arduinoml;


/******************
 ** Parser rules **
 ******************/

root            :   /*declaration*/ bricks states initial transitions declaration EOF;

//declaration     :   'application' name=IDENTIFIER;

bricks          :   (sensor|actuator)+;
    sensor      :    'sensor'   location ;
    actuator    :   'actuator'  location ;
    location    :   id=IDENTIFIER 'pin' port=PORT_NUMBER;

states          :   state+;
    state       :   'state'  name=IDENTIFIER  'means' (action ('and')?)+  ;
    action      :   receiver=IDENTIFIER 'becomes' value=IDENTIFIER;


initial :  'initial' starting=IDENTIFIER;



transitions     :   transition+;
    transition  :   'from' begin=IDENTIFIER 'to' end=IDENTIFIER 'when' combinationAction (','combination=IDENTIFIER combinationAction? ) ?;
    combinationAction:  (source=IDENTIFIER 'becomes' value=IDENTIFIER);


declaration     :   'export' name=APPLLICATION;
/*****************
 ** Lexer rules **
 *****************/

PORT_NUMBER     :   [1-9] | '10' |'11' | '12';
IDENTIFIER      :   LOWERCASE (LOWERCASE|UPPERCASE)+;
APPLLICATION      :  (LOWERCASE|UPPERCASE)+ ;
SIGNAL          :   'high' | 'low' ;


/*************
 ** Helpers **
 *************/

fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
UNKNOWN_CHAR : . ;