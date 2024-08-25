lexer grammar LangLexer;

@lexer::header {
package lang.parser;
}

ALPHA: [A-Za-z];
ALPHA_UPPERCASE: [A-Z];
ALPHA_LOWERCASE: [a-z];
INT: [0-9]+;
FLOAT: [+-]? [0-9]+ '.' [0-9]*;
IDENT_UPPERCASE: ALPHA_UPPERCASE (ALPHA | [0-9] | '_')*;
IDENT_LOWERCASE: ALPHA_LOWERCASE (ALPHA | [0-9] | '_')*;

CHAR_NEWLINE: '\\n';
CHAR_TAB: '\\t';
CHAR_BACKSPACE: '\\b';
CHAR_CARRIAGE: '\\r';
CHAR_BACKSLASH: '\\\\';
CHAR_QUOTE: '\\\'';

// Keywords
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';
NEW: 'new';
VOID: 'void';
STRUCT: 'struct';
TYPEDEF: 'typedef';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
NULL: 'null';
TRUE: 'true';
FALSE: 'false';
READ: 'read';
ITERATE: 'iterate';
PRINT: 'print';
DATA: 'data';

// Basic Types
BTYPE: 'Int' | 'Float' | 'Char' | 'Bool';

// Symbols
ASSIGNMENT: '=';
EQ: '==';
NOT_EQ: '!=';
SEMI: ';';
TIMES: '*';
PLUS: '+';
MOD: '%';
COMMA: ',';
DOUBLE_COLON: '::';
COLON: ':';
SINGLE_QUOTE: '\'';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
DOT: '.';
MINUS: '-';
DIVIDE: '/';
NOT_EQUAL: '<>';
LESS_THAN_OR_EQUAL: '<=';
LESS_THAN: '<';
GREATER_THAN_OR_EQUAL: '>=';
GREATER_THAN: '>';
DOUBLE_AMPERSAND: '&&';
AMPERSAND: '&';
EXCLAMATION_MARK: '!';
DOUBLE_PIPE: '||';
PIPE: '|';

// Identifiers and literals
ID: IDENT_LOWERCASE;
TYPE: IDENT_UPPERCASE;
INT_LITERAL: INT;
FLOAT_LITERAL: FLOAT;

// Whitespace and comments
WS: [ \t\b\n\r]+ -> skip;
LINE_COMMENT: '--' ~[\r\n]* -> skip;
COMMENT: '{-' ~[}]* '-}' -> skip;

// Character literals
fragment CHAR_SINGLE_QUOTE_CONTENT:
	CHAR_NEWLINE
	| CHAR_TAB
	| CHAR_BACKSPACE
	| CHAR_CARRIAGE
	| CHAR_BACKSLASH
	| CHAR_QUOTE
	| ALPHA;

CHAR_LITERAL: '\'' CHAR_SINGLE_QUOTE_CONTENT '\'';