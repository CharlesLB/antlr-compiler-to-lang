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
		Object sizeValue = size.interpret(context);
		if (!(sizeValue instanceof Integer)) {
			throw new RuntimeException("Array size must be an integer.");
		}

		int size = (Integer) sizeValue;
		if (size == 0) {
			size = 100;
		}

		Object[] newArray = new Object[size];
		for (int i = 0; i < size; i++) {
			newArray[i] = getDefaultValueForType(type.toString());
		}

		System.out.println("Node NewArray: " + newArray + "  Tamanho: " + size);

		return newArray;
	}

	private Object getDefaultValueForType(String typeName) {
		return null;
	}
}
