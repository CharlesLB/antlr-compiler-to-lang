/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.types;

import lang.ast.definitions.Type;
import visitors.Visitor;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type ‘[’ ‘]’
 * 
 * @Example Int[]
 * @Example ID[]
 * 
 */
public class MatrixType extends Type {
	private Type baseType; // (ex: Int, Float, etc.)
	private int dimensions;

	public MatrixType(Type baseType, int dimensions) {
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

	public void accept(Visitor v) {
		v.visit(this);
	}
}