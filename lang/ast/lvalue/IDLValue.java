package lang.ast.lvalue;

import java.util.*;

/*EX: x = 10; */
public class IDLValue extends LValue {
	private String name;

	public IDLValue(int line, int pos, String name) {
		super(line, pos);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Object interpret(HashMap<String, Object> context) {
		if (!context.containsKey(name)) {
			throw new RuntimeException("Variable " + name + " is not defined.");
		}

		return context.get(name);
	}
}