parser grammar LangParser;
options {
	tokenVocab = LangLexer;
}

@parser::header {
package lang.parser;
import lang.ast.*;
}

prog: def+;

def: data | fun;

data: 'data' ID '{' decl* '}';

decl: ID '::' type ';';

fun: ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}';

params: ID '::' type (',' ID '::' type)*;

type: type '[' ']' | btype;

btype: BTYPE | ID;

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
	| INT_LITERAL
	| FLOAT_LITERAL
	| CHAR_LITERAL
	| lvalue
	| '(' exp ')'
	| 'new' type ('[' exp ']')?
	| ID '(' exps? ')' '[' exp ']';

lvalue: ID | lvalue '[' exp ']' | lvalue '.' ID;

exps: exp (',' exp)*;