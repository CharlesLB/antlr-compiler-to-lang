/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */

parser grammar LangParser;
options {
	tokenVocab = LangLexer;
}

@parser::header {
package lang.parser;

import lang.ast.*;
import lang.ast.definitions.*;
import lang.ast.expressions.*;
import lang.ast.expressions.literals.*;
import lang.ast.expressions.operators.*;
import lang.ast.lvalue.*;

import lang.ast.statements.commands.*;
import lang.ast.statements.data.*;
import lang.ast.types.*;
}

prog
	returns[StmtList ast]:
	s1 = stmt {$ast = new StmtList($s1.ast.getLine(), $s1.ast.getColumn(), $s1.ast);} (
		s2 = stmt {$ast = new StmtList($s2.ast.getLine(), $s2.ast.getColumn(), $ast, $s2.ast);}
	)* EOF;

stmt
	returns[Node ast]: d = def {$ast = $d.ast;};

def
	returns[Node ast]:
	d = data {$ast = $d.ast;}
	| f = fun {$ast = $f.ast;};

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

params
	returns[List<Param> paramsList]:
	id1 = ID '::' type1 = type {
		$paramsList = new ArrayList<Param>();
        
		if ($type1.ast != null) {
			$paramsList.add(new Param($id1.line, $id1.pos, new ID($id1.line, $id1.pos, $id1.text), $type1.ast));
		}
	} (
		',' id2 = ID '::' type2 = type {
			if ($type2.ast != null) {
				$paramsList.add(new Param($id2.line, $id2.pos, new ID($id2.line, $id2.pos, $id2.text), $type2.ast));
			}
		}
	)*;

type
	returns[Type ast]:
	t = (BTYPE | ID) {
        if ($t.getType() == BTYPE) {
            $ast = new Btype($t.line, $t.pos, $t.text);
        } else {
            $ast = new IDType($t.line, $t.pos, $t.text);  
        }
    } (
		'[' ']' {
		// Muda tipo base para matriz de dimensão 1 ao encontrar []
        $ast = new MatrixType($t.line, $t.pos, $ast, 1);      
    }
	)* {
        // A cada par de colchetes adicional, incrementa a dimensão
        if ($ast instanceof MatrixType) {
            int extraDimensions = _localctx.getChildCount() / 2 - 1;
            if (extraDimensions > 0) {
                ((MatrixType) $ast).setDimensions(1 + extraDimensions);
            }
        }
    };

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

expr
	returns[Expr ast]:
	compExpr {
        $ast = $compExpr.ast;
    }
	| 'new' t = type '[' exp = expr ']' {
    $ast = new NewArray($start.getLine(), $start.getCharPositionInLine(), $t.ast, $exp.ast);
    }
	| 'new' t = type {
        $ast = new NewObject($start.getLine(), $start.getCharPositionInLine(), $t.ast);
    };

lvalue
	returns[LValue ast]:
	ID { 
        $ast = new IDLValue($ID.line, $ID.pos, $ID.text); 
    }
	| lv = lvalue '[' exp = expr ']' { 
        $ast = new ArrayAccessLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast, $exp.ast); 
    }
	| lv = lvalue '.' attr = ID { 
        $ast = new AttrAccessLValue($lv.ast.getLine(), $lv.ast.getColumn(), $lv.ast, new IDLValue($attr.line, $attr.pos, $attr.text)); 
    };

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

compExpr
	returns[Expr ast]:
	left = addExpr op = '&&' right = addExpr {
        $ast = new And($op.line, $op.pos, $left.ast, $right.ast);
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
        $ast = new CharLiteral($CHAR_LITERAL.line, $CHAR_LITERAL.pos, $CHAR_LITERAL.text); 
    }
	| '(' expr ')' {
        $ast = $expr.ast;
    }
	| id = ID '(' args = exps? ')' '[' index = expr ']' {
        List<Expr> exprList = new ArrayList<>();
        if (_localctx.args != null) {
            exprList.addAll($args.astList); 
        }
        $ast = new ArrayAccess(new FunCall($id.line, $id.pos, new ID($id.line, $id.pos, $id.text), exprList), $index.ast);
    }
	| lval = lvalue {
        $ast = $lval.ast;
    };