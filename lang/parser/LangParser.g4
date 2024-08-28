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
	t = (BTYPE | ID) {
        if ($t.text.equals("BTYPE")) {
            $ast = new Btype($t.line, $t.pos, $t.text);
        } else {
            $ast = new IDType($t.line, $t.pos, $t.text);  
        }
    } (
		'[' ']' {
				// Muda tipo base para matriz de dimensão 1 ao encontrar []
        $ast = new MatrixType($t.line, $t.pos, $ast, 1);      }
	)* {
        // A cada par de colchetes adicional, incrementa a dimensão
        if ($ast instanceof MatrixType) {
            int extraDimensions = _localctx.getChildCount() / 2 - 1;
            if (extraDimensions > 0) {
                ((MatrixType) $ast).setDimensions(1 + extraDimensions);
            }
        }
    };

// cmd: '{' cmd* '}' | 'if' '(' expr ')' cmd | 'if' '(' expr ')' cmd 'else' cmd | 'iterate' '('
// expr')' cmd | 'print' expr ';' | 'return' expr (',' expr)* ';' | lvalue '=' expr ';' | 'read'
// lvalue ';' | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';'
cmd
	returns[Cmd ast]:
	'if' '(' cond = expr ')' thenCmd = cmd 'else' elseCmd = cmd {
        $ast = new If($cond.ast.getLine(), $cond.ast.getColumn(), $cond.ast, $thenCmd.ast, $elseCmd.ast);
    }
	| 'if' '(' cond = expr ')' thenCmd = cmd {
        $ast = new If($cond.ast.getLine(), $cond.ast.getColumn(), $cond.ast, $thenCmd.ast);
    }
	| 'iterate' '(' count = expr ')' body = cmd {
        $ast = new Iterate($count.ast.getLine(), $count.ast.getColumn(), $count.ast, $body.ast);
    }
	| 'print' ex = expr ';' {
        $ast = new Print($ex.ast.getLine(), $ex.ast.getColumn(), $ex.ast);
    }
	| 'return' exs += expr (',' exs += expr)* ';' {
        List<Expr> exprList = new ArrayList<>();
        for (ExprContext exCtx : $exs) {
            exprList.add(exCtx.ast);
        }
        $ast = new Return($start.getLine(), $start.getCharPositionInLine(), exprList);
    }
	| ID '=' expr ';' {
        $ast = new Assign($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), $expr.ast);
    }
	| lv = lvalue '=' ex = expr ';' {
        $ast = new AssignLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast, $ex.ast);
    }
	| 'read' lv = lvalue ';' {
        $ast = new ReadLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast);
    }
	| ID '(' es = exps? ')' (
		'<' lvs += lvalue (',' lvs += lvalue)* '>'
	)? ';' {
        List<Expr> exprList = new ArrayList<>();
        if (_localctx.es != null) {
            exprList.addAll($es.astList); 
        }

        List<LValue> lvalueList = new ArrayList<>();
        if (_localctx.lvs != null) {
            for (LvalueContext lvalueCtx : $lvs) {
                lvalueList.add(lvalueCtx.ast); 
            }
        }

        $ast = new FunLValue($ID.line, $ID.pos, new ID($ID.line, $ID.pos, $ID.text), exprList, lvalueList);
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

// exp: exp '&&' exp | exp '<' exp | exp '==' exp | exp '!=' exp | exp '+' exp | exp '-' exp | exp
// '*' exp | exp '/' exp | exp '%' exp | '!' exp | '-' exp | 'true' | 'false' | 'null' | INT_LITERAL
// | FLOAT_LITERAL | CHAR_LITERAL | '(' exp ')' | lvalue
/*ainda tem que fazer os 3: | 'new' type ('[' exp ']')? | ID '(' exps? ')' '[' exp ']';
 */

// Regras para expressões
expr
	returns[Expr ast]:
	lval = lvalue {
        $ast = $lval.ast;
    }
	| compExpr {
        $ast = $compExpr.ast;
    }
	| 'new' t = type '[' exp = expr ']' {
    $ast = new NewArray($start.getLine(), $start.getCharPositionInLine(), $t.ast, $exp.ast);
    }
	| 'new' t = type {
        $ast = new NewObject($start.getLine(), $start.getCharPositionInLine(), $t.ast);
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
	| 'true' {
        $ast = new BoolLiteral($start.getLine(), $start.getCharPositionInLine(), true);
    }
	| 'false' {
        $ast = new BoolLiteral($start.getLine(), $start.getCharPositionInLine(), false);
    }
	| 'null' {
        $ast = new NullLiteral($start.getLine(), $start.getCharPositionInLine());
    }
	| ID {
        $ast = new ID($ID.line, $ID.pos, $ID.text);
    }
	| INT_LITERAL {
        $ast = new IntLiteral($INT_LITERAL.line, $INT_LITERAL.pos, Integer.parseInt($INT_LITERAL.text));
    }
	| FLOAT_LITERAL {
        $ast = new FloatLiteral($FLOAT_LITERAL.line, $FLOAT_LITERAL.pos, Float.parseFloat($FLOAT_LITERAL.text));
    }
	| CHAR_LITERAL {
        $ast = new CharLiteral($CHAR_LITERAL.line, $CHAR_LITERAL.pos, $CHAR_LITERAL.text.charAt(1)); 
    }
	| '(' expr ')' {
        $ast = $expr.ast;
    };

// lvalue: ID | lvalue '[' expr ']' | lvalue '.' ID;
lvalue
	returns[LValue ast]:
	ID { 
        $ast = new IDLValue($ID.line, $ID.pos, $ID.text); 
    }
	| lv = lvalue '[' exp = expr ']' { 
        $ast = new ArrayAccessLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast, $exp.ast); 
    }
	| lv = lvalue '.' field = ID { 
        $ast = new FieldAccessLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast, new IDLValue($field.line, $field.pos, $field.text)); 
    };

// exps: expr (',' expr)*;
exps
	returns[List<Expr> astList]:
	e1 = expr {
        $astList = new ArrayList<Expr>();
        $astList.add($e1.ast);
    } (
		',' e2 = expr {
        $astList.add($e2.ast);
    }
	)*;