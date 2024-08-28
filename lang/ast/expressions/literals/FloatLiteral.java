package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.expressions.Expr;

public class FloatLiteral extends Expr {

	private float value;

	public FloatLiteral(int lin, int col, float value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		return (int) value; // Dependendo da implementação, você pode querer retornar o valor como float ou
							// int
	}
}
