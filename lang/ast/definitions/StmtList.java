/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.definitions;

import java.util.HashMap;

import lang.ast.Node;

/**
 * Representa uma lista de nós reponsável por contruir a AST.
 */
public class StmtList extends Node {

	private Node cmd1;
	private Node cmd2;

	public StmtList(int l, int c, Node c1, Node c2) {
		super(l, c);
		this.cmd1 = c1;
		this.cmd2 = c2;
	}

	public StmtList(int l, int c, Node cmd1) {
		super(l, c);
		this.cmd1 = cmd1;
		this.cmd2 = null;
	}

	public Node getCmd1() {
		return cmd1;
	}

	public Node getCmd2() {
		return cmd2;
	}

	@Override
	public String toString() {
		String result = cmd1.toString();
		if (cmd2 != null) {
			result += "\n" + cmd2.toString();
		}
		return result;
	}

	public Object interpret(HashMap<String, Object> m) {
		Object result = cmd1.interpret(m);

		if (cmd2 == null)
			return result;
		else
			return cmd2.interpret(m);
	}

	// @Override
	public int getLine() {
		return super.getLine();
	}

	// @Override
	public int getColumn() {
		return super.getColumn();
	}

}
