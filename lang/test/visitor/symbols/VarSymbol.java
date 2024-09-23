/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

import lang.core.ast.definitions.Expr;

public class VarSymbol extends Symbol {
	private TypeSymbol type;
	private Expr value;

	public VarSymbol(String name, TypeSymbol type, Expr value) {
		super(name);
		this.type = type;
		this.value = value;
	}

	public TypeSymbol getType() {
		return type;
	}

	public Expr getValue() {
		return value;
	}

	public void setValue(Expr value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "VarSymbol{name='" + getName() + "', type=" + type.getName() + ", value=" + value + "}";
	}
}