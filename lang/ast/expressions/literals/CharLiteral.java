package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class CharLiteral extends Expr {

	private char value;

	public CharLiteral(int lin, int col, char value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		System.out.println("Node CharLiteral: " + value);
		return value; // Retorna o valor do caractere, que pode ser tratado como seu valor ASCII
	}
}
