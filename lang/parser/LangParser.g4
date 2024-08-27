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

		System.out.println("Parametros capturados: " + ((_localctx.p != null) ? "Sim" : "Não"));
		List<Param> paramsList = (_localctx.p != null) ? _localctx.p.paramsList : null;
		if (paramsList != null) {
			System.out.println("Conteúdo de paramsList:");
			for (Param param : paramsList) {
				System.out.println("  Parametro: " + param.getID().getName() + ", Tipo: " + param.getType());
			}
		} else {
			System.out.println("Nenhum parâmetro encontrado.");
		}


		$ast = new Fun($name.line, $name.pos,
					   new ID($name.line, $name.pos, $name.text),
					   paramsList,
					   returnTypes,
					   cmdList);
	};

// params: ID '::' type (',' ID '::' type)*;
params
	returns[List<Param> paramsList]:
	i1 = ID '::' t1 = type {
		$paramsList = new ArrayList<Param>();
		
		// Verificando se o AST foi gerado corretamente
		if ($t1.ast == null) {
			System.out.println("Erro: O AST para o tipo " + $i1.text + " está nulo.");
		} else {
			Param param = new Param($i1.line, $i1.pos, new ID($i1.line, $i1.pos, $i1.text), $t1.ast);
			System.out.println("Adicionando Param: " + param.getType());
			$paramsList.add(param);
		}
	} (
		',' i2 = ID '::' t2 = type {
			if ($t2.ast == null) {
				System.out.println("Erro: O AST para o tipo " + $i2.text + " está nulo.");
			} else {
				Param param = new Param($i2.line, $i2.pos, new ID($i2.line, $i2.pos, $i2.text), $t2.ast);
				System.out.println("Adicionando Param: " + param.getType());
				$paramsList.add(param);
			}
		}
	)*;

type
	returns[Type ast]:
	t = BTYPE {
				// Cria tipo base 
        $ast = new Btype($t.line, $t.pos, $t.text);
        System.out.println("BTYPE identificado: " + $t.text);
    } (
		'[' ']' {
				// Muda tipo base para matriz de dimensão 1 ao encontrar []
        System.out.println("Detectado ARRAY para: " + $ast);
        $ast = new MatrixType($t.line, $t.pos, (Btype) $ast, 1);  
        System.out.println("AST de MatrixType criado com 1 dimensão: " + $ast);
    }
	)* {
        // A cada par de colchetes adicional, incrementa a dimensão
        if ($ast instanceof MatrixType) {
            int extraDimensions = _localctx.getChildCount() / 2 - 1;
            if (extraDimensions > 0) {
                System.out.println("Adicionando dimensões extras: " + extraDimensions);
                ((MatrixType) $ast).setDimensions(1 + extraDimensions);
                System.out.println("Dimensões finais: " + ((MatrixType) $ast).getDimensions());
            }
        }
    };

cmd
	returns[Cmd ast]:
	c = .*? ';' {
        $ast = new Cmd($c.line, $c.pos, $c.getText());
    };

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