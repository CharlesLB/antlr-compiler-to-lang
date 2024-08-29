package lang.ast.expressions;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class ArrayAccess extends Expr {
	private Expr array;
	private Expr index;

	public ArrayAccess(Expr array, Expr index) {
		super(array.getLine(), array.getColumn());
		this.array = array;
		this.index = index;
	}

	public Expr getArray() {
		return array;
	}

	public Expr getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return array.toString() + "[" + index.toString() + "]";
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		return 1; // Mudar
	}
}
