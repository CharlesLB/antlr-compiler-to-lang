/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import lang.ast.definitions.Expr;

/**
 * Essa classe representa uma operação binária.
 *
 * @Expr Expr OP Expr
 *
 * @Example 2 + 1
 * @Example 1.0 - 2.0
 * @Example 1 * 2
 * @Example 1 == 2
 * @Example 1 != 2
 * @Example 1 < 2
 * @Example !1
 * @Example 1 && 2
 *
 * @Error 2 > 1 -> Não existe Greater Than
 * @Error 2 || 1 -> Não existe OR
 * @Error 2 <= 1 -> Não existe Less Than or Equal
 * @Error 2 >= 1 -> Não existe Greater Than or Equal
 */
public abstract class BinOP extends Expr {

	private Expr l;
	private Expr r;

	public BinOP(Expr l, Expr r) {
		this.l = l;
		this.r = r;
	}

	public void setLeft(Expr n) {
		l = n;
	}

	public void setRight(Expr n) {
		r = n;
	}

	public Expr getLeft() {
		return l;
	}

	public Expr getRight() {
		return r;
	}

}