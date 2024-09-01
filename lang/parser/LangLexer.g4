lexer grammar LangLexer;

@lexer::header {
package lang.parser;
}

// Keywords
IF: 'if';
ELSE: 'else';
RETURN: 'return';
NEW: 'new';
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
LESS_THAN: '<';
GREATER_THAN: '>';
DOUBLE_AMPERSAND: '&&';
EXCLAMATION_MARK: '!';

// Identifiers and literals
ID: IDENT_LOWERCASE | TYPE;
// PROFESSOR: o ID pose ser Uppercase? Exemplo: data Racional, o data é um ID, não um type
TYPE: IDENT_UPPERCASE;
INT_LITERAL: INT;
FLOAT_LITERAL: FLOAT;

// Whitespace and comments
WS: [ \t\b\n\r]+ -> skip;
LINE_COMMENT: '--' ~[\r\n]* -> skip;
COMMENT: '{-' ~[}]* '-}' -> skip;

// Character literals
fragment CHAR_SINGLE_QUOTE_CONTENT:
	~['\\] // Qualquer caractere, exceto aspas simples e barra invertida
	| '\\' .; // Um caractere de escape, como '\n', '\t', etc.

CHAR_LITERAL: '\'' CHAR_SINGLE_QUOTE_CONTENT '\'';

ALPHA: [A-Za-z];
ALPHA_UPPERCASE: [A-Z];
ALPHA_LOWERCASE: [a-z];
INT: [0-9]+;
FLOAT: [+-]? [0-9]+ '.' [0-9]*;
IDENT_UPPERCASE: ALPHA_UPPERCASE (ALPHA | [0-9] | '_')*;
IDENT_LOWERCASE: ALPHA_LOWERCASE (ALPHA | [0-9] | '_')*;