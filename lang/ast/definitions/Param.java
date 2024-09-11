/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.definitions;

import visitors.Visitor;

import lang.ast.Node;
import lang.ast.expressions.ID;

/**
 * Representa a definição do parâmetro de uma função.
 * 
 * @Parser ID ‘::’ type {‘,’ ID ‘::’ type}
 * 
 * @Example a :: Int, b :: Int
 */
public class Param extends Node {
	private ID id;
	private Type t;

	public Param(ID id, Type type) {
		this.id = id;
		this.t = type;
	}

	public ID getID() {
		return id;
	}

	public Type getType() {
		return t;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}