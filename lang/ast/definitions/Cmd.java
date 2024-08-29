package lang.ast.definitions;

import lang.ast.Node;

public abstract class Cmd extends Node {
	public Cmd(int line, int column) {
		super(line, column);
	}
}
