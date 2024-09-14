/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
lexer grammar LangLexer;

@lexer::header {
package lang.core.lexer;
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
TYPE: IDENT_UPPERCASE;
INT_LITERAL: INT;
FLOAT_LITERAL: FLOAT;

// Whitespace and comments
WS: [ \t\b\n\r]+ -> skip;
LINE_COMMENT: '--' ~[\r\n]* -> skip;
COMMENT: '{-' ~[}]* '-}' -> skip;

fragment CHAR_SINGLE_QUOTE_CONTENT:
	~['\\\r\n] // Qualquer caractere, exceto aspas simples, barra invertida e quebras de linha
	| '\\' [btnrf\\'"] // Captura escapes como \b, \t, \n, \r, \f, \\, \', \"
	| '\\' [\r\n]; // Captura especificamente a sequência de escape para nova linha

CHAR_LITERAL: '\'' CHAR_SINGLE_QUOTE_CONTENT '\'';

ALPHA: [A-Za-z];
ALPHA_UPPERCASE: [A-Z];
ALPHA_LOWERCASE: [a-z];
INT: [0-9]+;
FLOAT: [+-]? [0-9]+ '.' [0-9]*;
IDENT_UPPERCASE: ALPHA_UPPERCASE (ALPHA | [0-9] | '_')*;
IDENT_LOWERCASE: ALPHA_LOWERCASE (ALPHA | [0-9] | '_')*;