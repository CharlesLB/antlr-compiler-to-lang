package lang.ast;

import java.util.HashMap;

public class LValue extends Node {

	public LValue(int l, int c) {
		super(l, c);
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
