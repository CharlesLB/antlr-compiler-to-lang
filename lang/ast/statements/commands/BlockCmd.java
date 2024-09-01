/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import java.util.HashMap;
import java.util.List;

import lang.ast.definitions.Cmd;

/**
 * Escapsulamento dos comandos que estão dentro do IF.
 * 
 * @Parser { cmd* }
 * 
 * @example {
 *          print 1;
 *          print 2;
 *          }
 */
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
	public Object interpret(HashMap<String, Object> context) {
		Object lastResult = null;
		for (Cmd command : commands) {
			lastResult = command.interpret(context);
		}
		return lastResult;
	}
}