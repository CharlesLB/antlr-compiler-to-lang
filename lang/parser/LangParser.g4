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
	d = data {$ast = $d.ast;}
	| f = fun {$ast = $f.ast;};

// data: 'data' ID '{' decl* '}';
data
	returns[Data ast]:
	'data' ID '{' ds += decl* '}' { 
        List<Decl> declList = new ArrayList<>();
        for (DeclContext declCtx : $ds) {
            declList.add(declCtx.ast); // declCtx.ast é do tipo Decl
        }
        $ast = new Data($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), declList);
    };

// decl: ID '::' type ';';
decl
	returns[Decl ast]:
	ID '::' t = BTYPE ';' {
        $ast = new Decl($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), new Btype($t.line, $t.pos, $t.text));
    };

// fun: ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}';
fun
	returns[Fun ast]:
	name = ID '(' p = params? ')' (
		':' rt += type (',' rt += type)*
	)? '{' body += cmd* '}' {
		List<Type> returnTypes = new ArrayList<>();
		if ($rt != null) {
			for (TypeContext t : $rt) {
				returnTypes.add(t.ast);
			}
		}

		List<Cmd> cmdList = new ArrayList<>();
		if ($body != null) {
			for (CmdContext c : $body) {
				cmdList.add(c.ast);
			}
		}

		List<Param> paramsList = (_localctx.p != null) ? _localctx.p.paramsList : null;

		$ast = new Fun($name.line, $name.pos, new ID($name.line, $name.pos, $name.text), 
		paramsList, returnTypes, cmdList);
	};

// params: ID '::' type (',' ID '::' type)*;
params
	returns[List<Param> paramsList]:
	i1 = ID '::' t1 = type {
		$paramsList = new ArrayList<Param>();
		
		if ($t1.ast != null) {
			$paramsList.add(new Param($i1.line, $i1.pos, new ID($i1.line, $i1.pos, $i1.text), $t1.ast));
		}
	} (
		',' i2 = ID '::' t2 = type {
			if ($t2.ast != null) {
				$paramsList.add(new Param($i2.line, $i2.pos, new ID($i2.line, $i2.pos, $i2.text), $t2.ast));
			}
		}
	)*;

/* btype: BTYPE | ID; => não estou mais fazendo isso, verificar depois */
//  type: type '[' ']' | btype;
type
	returns[Type ast]:
	t = BTYPE {
				// Cria tipo base 
        $ast = new Btype($t.line, $t.pos, $t.text);
    } (
		'[' ']' {
				// Muda tipo base para matriz de dimensão 1 ao encontrar []
        $ast = new MatrixType($t.line, $t.pos, (Btype) $ast, 1);      }
	)* {
        // A cada par de colchetes adicional, incrementa a dimensão
        if ($ast instanceof MatrixType) {
            int extraDimensions = _localctx.getChildCount() / 2 - 1;
            if (extraDimensions > 0) {
                ((MatrixType) $ast).setDimensions(1 + extraDimensions);
            }
        }
    };

// cmd: '{' cmd* '}' | 'if' '(' expr ')' cmd | 'if' '(' expr ')' cmd 'else' cmd | 'iterate' '(' expr
// ')' cmd | 'read' lvalue ';' | 'print' expr ';' | 'return' expr (',' expr)* ';' | lvalue '=' expr
// ';' | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';';
cmd
	returns[Cmd ast]:
	'if' '(' cond = expr ')' thenCmd = cmd {
        $ast = new If($cond.ast.getLine(), $cond.ast.getColumn(), $cond.ast, $thenCmd.ast);
    }
	| ID '=' expr ';' {
        $ast = new Assign($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), $expr.ast);
    }
	| '{' cmds += cmd* '}' {
        List<Cmd> cmdList = new ArrayList<>();
        for (CmdContext c : $cmds) {
            cmdList.add(c.ast);
        }
        int line = $cmds.isEmpty() ? $start.getLine() : $cmds.get(0).start.getLine();
        int column = $cmds.isEmpty() ? $start.getCharPositionInLine() : $cmds.get(0).start.getCharPositionInLine();
        $ast = new BlockCmd(line, column, cmdList);
    };

// expr returns[Exp ast]: term {$ast = $term.ast;} ( '+' right = term { $ast = new
// Add($ast.getLine(), $ast.getColumn(), $ast, $right.ast); } )*;

// term returns[Exp ast]: ID {$ast = new ID($ID.line, $ID.pos, $ID.text);} | INT {$ast = new
// IntLiteral($INT.line, $INT.pos, Integer.parseInt($INT.text));};

// exp: exp '&&' exp | exp '<' exp | exp '==' exp | exp '!=' exp | exp '+' exp | exp '-' exp | exp
// '*' exp | exp '/' exp | exp '%' exp | '!' exp | '-' exp | 'true' | 'false' | 'null' | INT_LITERAL
// | FLOAT_LITERAL | CHAR_LITERAL | lvalue | '(' exp ')' | 'new' type ('[' exp ']')? | ID '(' exps?
// ')' '[' exp ']';

// Regras para expressões
expr
	returns[Expr ast]:
	compExpr {
        $ast = $compExpr.ast;
    };

compExpr
	returns[Expr ast]:
	left = addExpr op = '&&' right = addExpr {
        $ast = new DoubleAmpersand($op.line, $op.pos, $left.ast, $right.ast);
    }
	| left = addExpr op = '<' right = addExpr {
				$ast = new LessThan($op.line, $op.pos, $left.ast, $right.ast);
    }
	| left = addExpr op = '==' right = addExpr {
        $ast = new EQ($op.line, $op.pos, $left.ast, $right.ast);
    }
	| left = addExpr op = '!=' right = addExpr {
        $ast = new NotEq($op.line, $op.pos, $left.ast, $right.ast);
    }
	| addExpr {
        $ast = $addExpr.ast;
    };

// Regras para adição e subtração
addExpr
	returns[Expr ast]:
	mulExpr op = '+' right = addExpr {
        $ast = new Plus($op.line, $op.pos, $mulExpr.ast, $right.ast);
    }
	| mulExpr op = '-' right = addExpr {
        $ast = new Minus($op.line, $op.pos, $mulExpr.ast, $right.ast);
    }
	| mulExpr {
        $ast = $mulExpr.ast;
    };

// Regras para multiplicação e divisão
mulExpr
	returns[Expr ast]:
	factor op = '*' right = mulExpr {
        $ast = new Mul($op.line, $op.pos, $factor.ast, $right.ast);
    }
	| factor op = '/' right = mulExpr {
        $ast = new Div($op.line, $op.pos, $factor.ast, $right.ast);
    }
	| factor op = '%' right = mulExpr {
        $ast = new Mod($op.line, $op.pos, $factor.ast, $right.ast);
    }
	| factor {
        $ast = $factor.ast;
    };

// Fatores simples (identificadores e números)
factor
	returns[Expr ast]:
	'-' expr {
        $ast = new Neg($start.getLine(), $start.getCharPositionInLine(), $expr.ast);
    }
	| '!' expr {
        $ast = new Not($start.getLine(), $start.getCharPositionInLine(), $expr.ast);
    }
	| ID {
        $ast = new ID($ID.line, $ID.pos, $ID.text);
    }
	| INT_LITERAL {
        $ast = new IntLiteral($INT_LITERAL.line, $INT_LITERAL.pos, Integer.parseInt($INT_LITERAL.text));
    }
	| '(' expr ')' {
        $ast = $expr.ast;
    };

// // lvalue: ID | lvalue '[' expr ']' | lvalue '.' ID; lvalue returns[LValue ast]: ID { $ast = new
// ID($ID.line, $ID.pos, $ID.text); } | lvalue '[' expr ']' { $ast = new
// ArrayAccessLValue($lvalue.ast.getLine(), $lvalue.ast.getColumn(), $lvalue.ast, $expr.ast); } |
// lvalue '.' ID { $ast = new FieldAccessLValue($lvalue.ast.getLine(), $lvalue.ast.getColumn(),
// $lvalue.ast, new ID($ID.line, $ID.pos, $ID.text)); };

// exps: expr (',' expr)*;