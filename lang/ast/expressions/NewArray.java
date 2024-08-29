package lang.ast.expressions;

import java.util.HashMap;

import lang.ast.definitions.Expr;
import lang.ast.definitions.Type;

public class NewArray extends Expr {
	private Type type;
	private Expr size;

	public NewArray(int line, int column, Type type, Expr size) {
		super(line, column);
		this.type = type;
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public Expr getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "new " + type.toString() + "[" + size.toString() + "]";
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		return 1; // Tem que mudar
	}
}
