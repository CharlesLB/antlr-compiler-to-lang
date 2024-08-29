package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.lvalue.LValue;

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
	public Object interpret(HashMap<String, Object> context) {
		return 1; // Tem que mudar
	}
}