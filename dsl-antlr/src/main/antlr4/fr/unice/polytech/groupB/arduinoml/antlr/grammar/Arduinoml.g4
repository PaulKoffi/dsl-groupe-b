grammar Arduinoml;


/******************
 ** Parser rules **
 ******************/

root            :    bricks states initial transitions declaration EOF;

declaration     :   'export' name=APPLLICATION;

bricks          :   (sensor|actuator)+;
    sensor      :    'sensor'   location ;
    actuator    :   'actuator'  location ;
    location    :   id=DEFINITION 'pin' port=PORT_NUMBER;

states          :   state+;
    state       :   'state'  name=DEFINITION  'means' (action (operator= OPERATOR)?)+  ;
    action      :   receiver=IDENTIFIER 'becomes' value=SIGNAL;


initial :  'initial' starting=IDENTIFIER;



transitions     :   transition+;
    transition  :   ('from'|'fromC') begin=IDENTIFIER 'to' end=IDENTIFIER  combinationAction (',' combination=OPERATOR combinationAction )?;
    combinationAction:  'when' source=IDENTIFIER 'becomes' value=SIGNAL;




/*****************
 ** Lexer rules **
 *****************/
OPERATOR        :   'and' | 'or' ;
SIGNAL          :   'high' | 'low' ;

PORT_NUMBER     :   [1-9] | '10' |'11' | '12';
DEFINITION      :   '"' LOWERCASE (LOWERCASE|UPPERCASE)+ NUMBER? '"';
IDENTIFIER      :   LOWERCASE (LOWERCASE|UPPERCASE)+ NUMBER?;

APPLLICATION      : '"' (LOWERCASE|UPPERCASE) (LOWERCASE|UPPERCASE|' ')+ '"';




/*************
 ** Helpers **
 *************/

fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
fragment NUMBER     : [0-9]+;
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
