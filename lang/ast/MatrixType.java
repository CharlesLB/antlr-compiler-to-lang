package lang.ast;

import java.util.HashMap;

public class MatrixType extends Type {
	private Type baseType; // O tipo base (ex: Int, Float, etc.)
	private int dimensions;

	public MatrixType(int l, int c, Type baseType, int dimensions) {
		super(l, c);
		this.baseType = baseType;
		this.dimensions = dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public Type getBaseType() {
		return baseType;
	}

	public int getDimensions() {
		return dimensions;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(baseType.toString());
		for (int i = 0; i < dimensions; i++) {
			sb.append("[]");
		}

		return sb.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1;
	}
}