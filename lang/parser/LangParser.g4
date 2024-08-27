parser grammar LangParser;
options {
	tokenVocab = LangLexer;
}

@parser::header {
package lang.parser;
import lang.ast.*;
}

// prog: def+;
prog
	returns[StmtList ast]:
	s1 = stmt {$ast = new StmtList($s1.ast.getLine(), $s1.ast.getColumn(), $s1.ast);} (
		s2 = stmt {$ast = new StmtList($s2.ast.getLine(), $s2.ast.getColumn(), $ast, $s2.ast);}
	)* EOF;

stmt
	returns[Node ast]: d = def {$ast = $d.ast;};

// def: data | fun;
def
	returns[Node ast]:
	d = data {$ast = $d.ast;}; // | f = fun {$ast = f.ast;};

data
	returns[Data ast]:
	'data' ID '{' ds += decl* '}' { 
        List<Decl> declList = new ArrayList<>();
        for (DeclContext declCtx : $ds) {
            declList.add(declCtx.ast); // declCtx.ast é do tipo Decl
        }
        $ast = new Data($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), declList);
    };

decl
	returns[Decl ast]:
	ID '::' t = BTYPE ';' {
        $ast = new Decl($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), new Btype($t.line, $t.pos, $t.text));
    };

// id = ID '::' t = type ';' { $ast = new DeclarationNode(id.getLine(), id.getCharPositionInLine(),
// id.getText(), $t.ast); };

// fun: ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}'; fun returns[Node ast]: id = ID '('
// params = params? ')' ( ':' types = type (',' types = type)* )? '{' cmds = cmd* '}' { $ast = new
// FunctionNode(id.getLine(), id.getCharPositionInLine(), id.getText(), $params, $types, $cmds); };

// params: ID '::' type (',' ID '::' type)*; params returns[List<ParameterNode> ast]: id1 = ID '::'
// t1 = type { $ast = new ArrayList<>(); $ast.add(new ParameterNode(id1.getLine(),
// id1.getCharPositionInLine(), id1.getText(), $t1.ast)); } ( ',' id2 = ID '::' t2 = type {
// $ast.add(new ParameterNode(id2.getLine(), id2.getCharPositionInLine(), id2.getText(), $t2.ast));
// } )*;

// type: type '[' ']' | btype; type returns[TypeNode ast]: t1 = type '[' ']' { $ast = new
// ArrayTypeNode(t1.ast.getLine(), t1.ast.getColumnumn(), t1.ast); } | b = btype { $ast = new
// BaseTypeNode(b.getLine(), b.getCharPositionInLine(), b.getText()); };

// btype: BTYPE | ID;

// cmd: '{' cmd* '}' | 'if' '(' exp ')' cmd | 'if' '(' exp ')' cmd 'else' cmd | 'iterate' '(' exp
// ')' cmd | 'read' lvalue ';' | 'print' exp ';' | 'return' exp (',' exp)* ';' | lvalue '=' exp ';'
// | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';';

// exp: exp '&&' exp | exp '<' exp | exp '==' exp | exp '!=' exp | exp '+' exp | exp '-' exp | exp
// '*' exp | exp '/' exp | exp '%' exp | '!' exp | '-' exp | 'true' | 'false' | 'null' | INT_LITERAL
// | FLOAT_LITERAL | CHAR_LITERAL | lvalue | '(' exp ')' | 'new' type ('[' exp ']')? | ID '(' exps?
// ')' '[' exp ']';

// lvalue: ID | lvalue '[' exp ']' | lvalue '.' ID;

// exps: exp (',' exp)*;