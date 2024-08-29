package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.Node;
import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

public class If extends Cmd {

	private Expr exp;
	private Node thn;
	private Node els;

	public If(int lin, int col, Expr exp, Node thn, Node els) {
		super(lin, col);
		this.exp = exp;
		this.thn = thn;
		this.els = els;
	}

	public If(int lin, int col, Expr exp, Node thn) {
		super(lin, col);
		this.exp = exp;
		this.thn = thn;
		this.els = null;
	}

	public Expr getExp() {
		return exp;
	}

	public Node getThenCmd() {
		return thn;
	}

	public Node getThenEls() {
		return els;
	}

	public String toString() {
		String s = exp.toString();
		String sthn = thn.toString();
		String sels = els != null ? " : " + els.toString() : "";
		if (thn instanceof If) {
			sthn = "(" + sthn + ")";
		}
		if (els != null && els instanceof If) {
			sels = "(" + sels + ")";
		}
		s += " ? " + sthn + sels;
		return s;
	}

	public int interpret(HashMap<String, Integer> m) {
		int n = exp.interpret(m);
		if (n != 0) {
			return thn.interpret(m);
		} else if (els != null) {
			return els.interpret(m);
		}
		return n;
	}

}
