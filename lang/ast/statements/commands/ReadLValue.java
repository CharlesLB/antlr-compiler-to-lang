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
		// DUVIDAS: É no interpret que fica isso?
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		System.out.print("Enter a value: ");
		Object inputValue = null;
		if (scanner.hasNextInt()) {
			inputValue = scanner.nextInt();
		} else if (scanner.hasNextDouble()) {
			inputValue = scanner.nextDouble();
		} else {
			// Para caracter
			inputValue = scanner.next();
		}

		context.put(this.getLValue().toString(), inputValue);

		System.out.println("-- Read: " + inputValue + " into " + this.getLValue().toString());

		return null;
	}
}