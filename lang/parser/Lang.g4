grammar Lang;

@parser::header {
package lang.parser;
import lang.ast.*;

}

@lexer::header {
package lang.parser;
}

prog: def+;

def: data | fun;

data: 'data' ID '{' decl* '}';

decl: ID '::' type ';';

fun: ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}';

params: ID '::' type (',' ID '::' type)*;

type: type '[' ']' | btype;

btype: 'Int' | 'Char' | 'Bool' | 'Float' | ID;

cmd:
	'{' cmd* '}'
	| 'if' '(' exp ')' cmd
	| 'if' '(' exp ')' cmd 'else' cmd
	| 'iterate' '(' exp ')' cmd
	| 'read' lvalue ';'
	| 'print' exp ';'
	| 'return' exp (',' exp)* ';'
	| lvalue '=' exp ';'
	| ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';';

exp:
	exp '&&' exp
	| exp '<' exp
	| exp '==' exp
	| exp '!=' exp
	| exp '+' exp
	| exp '-' exp
	| exp '*' exp
	| exp '/' exp
	| exp '%' exp
	| '!' exp
	| '-' exp
	| 'true'
	| 'false'
	| 'null'
	| INT
	| FLOAT
	| CHAR
	| lvalue
	| '(' exp ')'
	| 'new' type ('[' exp ']')?
	| ID '(' exps? ')' '[' exp ']';

lvalue: ID | lvalue '[' exp ']' | lvalue '.' ID;

exps: exp (',' exp)*;

// Lexer Rules
ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]*;
CHAR: '\'' . '\'';

TRUE: 'true';
FALSE: 'false';
NULL: 'null';

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;