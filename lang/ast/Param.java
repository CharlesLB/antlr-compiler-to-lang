package lang.ast;

import java.util.HashMap;

public class Param extends Node {
	private ID id;
	private Type t;

	public Param(int l, int c, ID id, Type type) {
		super(l, c);
		this.id = id;
		this.t = t;
	}

	public ID getID() {
		return id;
	}

	public Type getType() {
		return t;
	}

	@Override
	public String toString() {
		return id.toString() + " :: " + t.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
