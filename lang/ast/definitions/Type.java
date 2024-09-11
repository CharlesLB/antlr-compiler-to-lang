/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
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
	public Type() {

	}
}