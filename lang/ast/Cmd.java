package lang.ast;

import java.util.HashMap;

public class Cmd extends Node {
	private String command;

	public Cmd(int l, int c, String command) {
		super(l, c);
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	@Override
	public String toString() {
		return command + ";";
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
