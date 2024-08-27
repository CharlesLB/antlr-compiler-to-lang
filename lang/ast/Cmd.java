package lang.ast;

import java.util.HashMap;

public abstract class Cmd extends Node {
	public Cmd(int line, int column) {
		super(line, column);
	}
}
