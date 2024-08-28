package lang.ast;

import java.util.HashMap;

public abstract class Node extends SuperNode {

	private int line, col;

	public Node(int l, int c) {
		line = l;
		col = c;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return col;
	}

	public abstract int interpret(HashMap<String, Integer> m);

}