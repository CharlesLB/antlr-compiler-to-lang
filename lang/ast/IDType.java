package lang.ast;

import java.util.HashMap;

public class IDType extends Type {
	private String id;

	public IDType(int line, int column, String id) {
		super(line, column);
		this.id = id;
	}

	public String getName() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	public int interpret(HashMap<String, Integer> m) {
		return m.get(id);
	}
}