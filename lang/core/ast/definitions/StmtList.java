/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.definitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lang.core.ast.Node;
import lang.test.visitor.Visitable;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Representa uma lista de nós responsável por construir a AST.
 */
public class StmtList extends Node implements Visitable {

	private List<Node> commands;

	public StmtList(int l, int c, List<Node> commands) {
		super(l, c);
		this.commands = commands;
	}

	public List<Node> getCommands() {
		return commands;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Node cmd : commands) {
			result.append(cmd.toString()).append("\n");
		}
		return result.toString().trim(); // Remove the last new line
	}

	public Object interpret(HashMap<String, Object> m) {
		Object result = null;
		for (Node cmd : commands) {
			result = cmd.interpret(m);
		}
		return result;
	}

	@Override
	public int getLine() {
		return super.getLine();
	}

	@Override
	public int getColumn() {
		return super.getColumn();
	}

	@Override
	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}
