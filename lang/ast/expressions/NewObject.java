package lang.ast.expressions;

import java.util.HashMap;

import lang.ast.definitions.Expr;
import lang.ast.definitions.Type;

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
	public Object interpret(HashMap<String, Object> context) {
		HashMap<String, Object> newObject = new HashMap<>();

		System.out.println("Node NewObject NÃO IMPLEMENTADO ");

		return newObject;
	}
}