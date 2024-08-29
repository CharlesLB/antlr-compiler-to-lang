package lang.ast.statements.commands;

import java.util.HashMap;
import java.util.List;

import lang.ast.definitions.Cmd;

public class BlockCmd extends Cmd {
	private List<Cmd> commands;

	public BlockCmd(int line, int column, List<Cmd> commands) {
		super(line, column);
		this.commands = commands;
	}

	public List<Cmd> getCommands() {
		return commands;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (Cmd cmd : commands) {
			sb.append(cmd.toString()).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}