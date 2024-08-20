// Nome: Charles Lelis Braga - Matrícula: 202035015
// Nome: Gabriella Carvalho -- Matrícula: 202165047AC

package lang.parser;

import beaver.Symbol;
import beaver.Scanner;
import java.math.BigDecimal;

%%
%public
%class LangLexer
%extends Scanner
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%line
%column
%{
	private Symbol newToken(short id) {
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength());
	}

	private Symbol newToken(short id, Object value) {
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
	}
%}

ALPHA = [A-Za-z]
// ALPHA_UPPERCASE=[A-Z]
ALPHA_LOWERCASE=[a-z]

// INT= [:digit:] [:digit:]*  
// FLOAT= [-+]?([:digit:]+ \. [:digit:]*)
// IDENT_UPPERCASE = {ALPHA_UPPERCASE}({ALPHA}|:digit:|_)*
IDENT_LOWERCASE = {ALPHA_LOWERCASE}({ALPHA}|:digit:|_)*

// NEW_LINE=\r|\n|\r\n
// WHITE_SPACE_CHAR=[\n\r\ \t\b]

// CHAR_NEWLINE = \\n
// CHAR_TAB = \\t
// CHAR_BACKSPACE = \\b
// CHAR_CARRIAGE = \\r
// CHAR_BACKSLASH = \\\\
// CHAR_QUOTE = \\\'

%state COMMENT
%state LINE_COMMENT
%state CHAR_SINGLE_QUOTE
%state END_CHAR_SINGLE_QUOTE
%%

<YYINITIAL>{
  /* RESERVED_WORDS */
    // "if" { return newToken(Terminals.IF); }
    // "else" { return newToken(Terminals.ELSE); }
    // "while" { return newToken(Terminals.WHILE); }
    // "for" { return newToken(Terminals.FOR); }
    // "return" { return newToken(Terminals.RETURN); }
    // "break" { return newToken(Terminals.BREAK); }
    // "continue" { return newToken(Terminals.CONTINUE); }
    // "new" { return newToken(Terminals.NEW); }
    // "void" { return newToken(Terminals.VOID); }
    // "struct" { return newToken(Terminals.STRUCT); }
    // "typedef" { return newToken(Terminals.TYPEDEF); }
    // "switch" { return newToken(Terminals.SWITCH); }
    // "case" { return newToken(Terminals.CASE); }
    // "default" { return newToken(Terminals.DEFAULT); }
    // "null" { return newToken(Terminals.NULL); }
    // "true" { return newToken(Terminals.BOOL); }
    // "false" { return newToken(Terminals.BOOL); }
    // "print" { return newToken(Terminals.PRINT); }
    // "scan" { return newToken(Terminals.SCAN); }    

    // "Int" { return newToken(Terminals.BTYPE); }
    // "Float" { return newToken(Terminals.BTYPE); }
    // "Char" { return newToken(Terminals.BTYPE); }
    // "Bool" { return newToken(Terminals.BTYPE); }

    {IDENT_LOWERCASE} { return newToken(Terminals.ID, yytext()); }
    // {IDENT_UPPERCASE} { return newToken(Terminals.TYPE, yytext()); }
    // {INT} { return newToken(Terminals.INT, yytext()); }
    // {FLOAT} { return newToken(Terminals.FLOAT, yytext()); }

    // "--" { yybegin(LINE_COMMENT); }
    // "{-" { yybegin(COMMENT); }
    
    "=" { return newToken(Terminals.ASSIGNMENT); }
    // "==" { return newToken(Terminals.EQ); }
    // "!=" { return newToken(Terminals.NOT_EQ); }
    ";" { return newToken(Terminals.SEMI); }
    // "*" { return newToken(Terminals.TIMES); }
    "+" { return newToken(Terminals.PLUS); }
    // "%" { return newToken(Terminals.MOD); }
    // "," { return newToken(Terminals.COMMA); }
    // "::" { return newToken(Terminals.DOUBLE_COLON); }
    // ":" { return newToken(Terminals.COLON); }

    // "'" { yybegin(CHAR_SINGLE_QUOTE); return newToken(Terminals.SINGLE_QUOTE); }
    
    // "(" { return newToken(Terminals.LEFT_PAREN); }
    // ")" { return newToken(Terminals.RIGHT_PAREN); }
    // "[" { return newToken(Terminals.LEFT_BRACKET); }
    // "]" { return newToken(Terminals.RIGHT_BRACKET); }
    // "{" { return newToken(Terminals.LEFT_BRACE); }
    // "}" { return newToken(Terminals.RIGHT_BRACE); }
    // "." { return newToken(Terminals.DOT); }
    // "-" { return newToken(Terminals.MINUS); }
    // "/" { return newToken(Terminals.DIVIDE); }
    // "<>" { return newToken(Terminals.NOT_EQUAL); }
    // "<=" { return newToken(Terminals.LESS_THAN_OR_EQUAL); }
    // "<" { return newToken(Terminals.LESS_THAN); }
    // ">=" { return newToken(Terminals.GREATER_THAN_OR_EQUAL); }
    // ">" { return newToken(Terminals.GREATER_THAN); }
    // "&&" { return newToken(Terminals.DOUBLE_AMPERSAND); }
    // "&" { return newToken(Terminals.AMPERSAND); }
    // "!" { return newToken(Terminals.EXCLAMATION_MARK); }
    // "||" { return newToken(Terminals.DOUBLE_PIPE); }
    // "|" { return newToken(Terminals.PIPE); }

    // {WHITE_SPACE_CHAR} { }
}

// <CHAR_SINGLE_QUOTE> {
//     {CHAR_NEWLINE} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {CHAR_TAB} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {CHAR_BACKSPACE} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {CHAR_CARRIAGE} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {CHAR_BACKSLASH} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {CHAR_QUOTE} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
//     {ALPHA} { yybegin(END_CHAR_SINGLE_QUOTE); return newToken(Terminals.CHAR); }
// }

// <END_CHAR_SINGLE_QUOTE> {
//   "\'" { yybegin(YYINITIAL); return newToken(Terminals.SINGLE_QUOTE); }
// }

// <COMMENT>{
//    "-}"     { yybegin(YYINITIAL); } 
//    [^"-}"]  {                     }
// }

// <LINE_COMMENT>{
//     {NEW_LINE} { yybegin(YYINITIAL); }
//     [^\n\r] { }
// }

// erros
[^]                 { throw new RuntimeException("Illegal character <"+yytext()+">"); }
