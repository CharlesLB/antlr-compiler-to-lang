package lang.ast.definitions;

import lang.ast.Node;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type ‘[’ ‘]’
 *         | btype
 * 
 * @Example Int[], Bool
 */
public abstract class Type extends Node {
	public Type(int l, int c) {
		super(l, c);
	}
}