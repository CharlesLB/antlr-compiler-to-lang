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

	@Override
	public String toString() {
		return "VarSymbol{name='" + getName() + "', type=" + type.getName() + ", value=" + value + "}";
	}
}