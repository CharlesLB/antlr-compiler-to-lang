package lang.ast.statements.commands;

import lang.ast.Node;

public abstract class Cmd extends Node {
	public Cmd(int line, int column) {
		super(line, column);
	}
}
