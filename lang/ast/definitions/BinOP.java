package lang.ast.definitions;

import lang.ast.expressions.Expr;

public abstract class BinOP extends Expr {

	private Expr l;
	private Expr r;

	public BinOP(int lin, int col, Expr l, Expr r) {
		super(lin, col);
		this.l = l;
		this.r = r;
	}

	public Expr getLeft() {
		return l;
	}

	public Expr getRight() {
		return r;
	}
}