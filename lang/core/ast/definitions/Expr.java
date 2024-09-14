/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.definitions;

import lang.core.ast.Node;

/**
 * Representa as expressões na linguagem.
 * 
 * @exp Operações lógicas e aritméticas
 * @Parser exp && exp
 * @Parser exp < exp
 * @Parser exp == exp
 * @Parser exp != exp
 * @Parser exp + exp
 * @Parser exp - exp
 * @Parser exp * exp
 * @Parser exp / exp
 * @Parser exp % exp
 * 
 * @exp Negação e valores unários
 * @Parser ! exp
 * @Parser - exp
 * 
 * @exp Literais e identificadores
 * @Parser true
 * @Parser false
 * @Parser null
 * @Parser INT
 * @Parser FLOAT
 * @Parser CHAR
 * @Parser lvalue
 * 
 * @exp Parênteses e construção de novos objetos
 * @Parser '(' exp ')'
 * @Parser new type [ '[' exp ']' ]
 * 
 * @exp Chamadas de função sem valor de retorno e acesso a elementos de array
 * @Parser ID '(' [exps] ')'
 * @Parser ID '(' [exps] ')' '[' exp ']'
 */
public abstract class Expr extends Node {
	public Expr(int l, int c) {
		super(l, c);
	}

}