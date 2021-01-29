grammar Arduinoml;


/******************
 ** Parser rules **
 ******************/

root            :    bricks states initial transitions declaration EOF;

declaration     :   'export' name=APPLLICATION;

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



/*****************
 ** Lexer rules **
 *****************/

PORT_NUMBER     :   [1-9] | '10' |'11' | '12';
IDENTIFIER      :   LOWERCASE (LOWERCASE|UPPERCASE)+ NUMBER?;
APPLLICATION      :  (LOWERCASE|UPPERCASE)+ ;
SIGNAL          :   'high' | 'low' ;


/*************
 ** Helpers **
 *************/

fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
fragment NUMBER     : [0-9]+;
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
UNKNOWN_CHAR : . ;