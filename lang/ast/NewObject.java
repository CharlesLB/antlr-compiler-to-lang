package lang.ast;

import java.util.HashMap;

public class NewObject extends Expr {
	private Type type;

	public NewObject(int line, int column, Type type) {
		super(line, column);
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "new " + type.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}