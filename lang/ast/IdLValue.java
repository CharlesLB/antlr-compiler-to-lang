package lang.ast;

import java.util.HashMap;

public class IdLValue extends LValue {
	String name;

	public IdLValue(String name, int line, int pos) {
		super(line, pos);
		this.name = name;
	}
}