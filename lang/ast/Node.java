package lang.ast;

import java.util.HashMap;

/**
 * Representa um nó da árvore sintática.
 */
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

	public abstract Object interpret(HashMap<String, Object> m);

}