package lang.ast;

import java.util.HashMap;

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
	public int interpret(HashMap<String, Integer> m) {
		return value; // Retorna o valor do caractere, que pode ser tratado como seu valor ASCII
	}
}
