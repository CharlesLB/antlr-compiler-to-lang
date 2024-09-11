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
	returns[Program ast]:
	defs = defList EOF {
        // Constroi um array de funções e dados e passa para o Program
        List<Fun> funs = new ArrayList<>();
        List<Data> datas = new ArrayList<>();

        for (Node def : $defs.ast) {
            if (def instanceof Fun) {
                funs.add((Fun) def);
            } else if (def instanceof Data) {
                datas.add((Data) def);
            }
        }

        $ast = new Program(funs.toArray(new Fun[0]), datas.toArray(new Data[0]));
    };

defList
	returns[List<Node> ast]:
	s1 = def {$ast = new ArrayList<>(); $ast.add($s1.ast);} (
		s2 = def {$ast.add($s2.ast);}
	)*;

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
        $ast = new Data(new ID($ID.text), declList);
    };

decl
	returns[Decl ast]:
	ID '::' t = BTYPE ';' {
        $ast = new Decl(new ID($ID.text), new Btype($t.text));
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

		$ast = new Fun(new ID($name.text), paramsList, returnTypes, cmdList);
	};

params
	returns[List<Param> paramsList]:
	id1 = ID '::' type1 = type {
		$paramsList = new ArrayList<Param>();
        
		if ($type1.ast != null) {
			$paramsList.add(new Param(new ID($id1.text), $type1.ast));
		}
	} (
		',' id2 = ID '::' type2 = type {
			if ($type2.ast != null) {
				$paramsList.add(new Param(new ID($id2.text), $type2.ast));
			}
		}
	)*;

type
	returns[Type ast]:
	t = (BTYPE | ID) {
        if ($t.getType() == BTYPE) {
            $ast = new Btype($t.text);
        } else {
            $ast = new IDType($t.text);  
        }
    } (
		'[' ']' {
		// Muda tipo base para matriz de dimensão 1 ao encontrar []
        $ast = new MatrixType($ast, 1);      
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
        $ast = new If($cond.ast, $thenCmd.ast, $elseCmd.ast);
    }
	| 'if' '(' cond = expr ')' thenCmd = cmd {
        $ast = new If($cond.ast, $thenCmd.ast);
    }
	| 'iterate' '(' count = expr ')' body = cmd {
        $ast = new Iterate($count.ast, $body.ast);
    }
	| 'print' ex = expr ';' {
        $ast = new Print($ex.ast);
    }
	| 'return' exs += expr (',' exs += expr)* ';' {
        List<Expr> exprList = new ArrayList<>();
        for (ExprContext exCtx : $exs) {
            exprList.add(exCtx.ast);
        }
        $ast = new Return(exprList);
    }
	| ID '=' expr ';' {
        $ast = new Assign(new ID($ID.text), $expr.ast);
    }
	| lv = lvalue '=' ex = expr ';' {
        $ast = new AssignLValue($lv.ast, $ex.ast);
    }
	| 'read' lv = lvalue ';' {
        $ast = new ReadLValue($lv.ast);
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

        $ast = new FunLValue(new ID($ID.text), exprList, lvalueList);
    }
	| '{' cmds += cmd* '}' {
        List<Cmd> cmdList = new ArrayList<>();
        for (CmdContext c : $cmds) {
            cmdList.add(c.ast);
        }
        
        $ast = new BlockCmd(cmdList);
    };

expr
	returns[Expr ast]:
	compExpr {
        $ast = $compExpr.ast;
    }
	| 'new' t = type '[' exp = expr ']' {
    $ast = new NewArray($t.ast, $exp.ast);
    }
	| 'new' t = type {
        $ast = new NewObject($t.ast);
    };

lvalue
	returns[LValue ast]:
	ID { 
        $ast = new IDLValue($ID.text); 
    }
	| lv = lvalue '[' exp = expr ']' { 
        $ast = new ArrayAccessLValue($lv.ast, $exp.ast); 
    }
	| lv = lvalue '.' attr = ID { 
        $ast = new AttrAccessLValue($lv.ast, new IDLValue($attr.text)); 
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
        $ast = new And($left.ast, $right.ast);
    }
	| left = addExpr op = '<' right = addExpr {
				$ast = new LessThan($left.ast, $right.ast);
    }
	| left = addExpr op = '==' right = addExpr {
        $ast = new EQ($left.ast, $right.ast);
    }
	| left = addExpr op = '!=' right = addExpr {
        $ast = new NotEq($left.ast, $right.ast);
    }
	| addExpr {
        $ast = $addExpr.ast;
    };

addExpr
	returns[Expr ast]:
	mulExpr op = '+' right = addExpr {
        $ast = new Plus($mulExpr.ast, $right.ast);
    }
	| mulExpr op = '-' right = addExpr {
        $ast = new Minus($mulExpr.ast, $right.ast);
    }
	| mulExpr {
        $ast = $mulExpr.ast;
    };

mulExpr
	returns[Expr ast]:
	factor op = '*' right = mulExpr {
        $ast = new Mul($factor.ast, $right.ast);
    }
	| factor op = '/' right = mulExpr {
        $ast = new Div($factor.ast, $right.ast);
    }
	| factor op = '%' right = mulExpr {
        $ast = new Mod($factor.ast, $right.ast);
    }
	| factor {
        $ast = $factor.ast;
    };

factor
	returns[Expr ast]:
	'-' expr {
        $ast = new Neg($expr.ast);
    }
	| '!' expr {
        $ast = new Not($expr.ast);
    }
	| 'true' {
        $ast = new BoolLiteral(true);
    }
	| 'false' {
        $ast = new BoolLiteral(false);
    }
	| 'null' {
        $ast = new NullLiteral();
    }
	| ID {
        $ast = new ID($ID.text);
    }
	| INT_LITERAL {
        $ast = new IntLiteral(Integer.parseInt($INT_LITERAL.text));
    }
	| FLOAT_LITERAL {
        $ast = new FloatLiteral(Float.parseFloat($FLOAT_LITERAL.text));
    }
	| CHAR_LITERAL {
        $ast = new CharLiteral($CHAR_LITERAL.text); 
    }
	| '(' expr ')' {
        $ast = $expr.ast;
    }
	| id = ID '(' args = exps? ')' '[' index = expr ']' {
        List<Expr> exprList = new ArrayList<>();
        if (_localctx.args != null) {
            exprList.addAll($args.astList); 
        }
        $ast = new ArrayAccess(new FunCall(new ID($id.text), exprList), $index.ast);
    }
	| lval = lvalue {
        $ast = $lval.ast;
    };