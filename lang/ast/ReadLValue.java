package lang.ast;

import java.util.HashMap;

public class ReadLValue extends Cmd {
	private LValue lvalue;

	public ReadLValue(int line, int column, LValue lvalue) {
		super(line, column);
		this.lvalue = lvalue;
	}

	public LValue getLValue() {
		return lvalue;
	}

	@Override
	public String toString() {
		return "read " + lvalue.toString() + ";";
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}