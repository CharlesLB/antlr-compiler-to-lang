/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import lang.ast.Node;
import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um comando de if.
 * 
 * @Parser if ‘(’ exp ‘)’ cmd
 * @Parser if ‘(’ exp ‘)’ cmd else cmd
 * 
 * @Example if (true) { print 1; }
 */
public class If extends Cmd {
	private Expr exp;
	private Node thn;
	private Node els;

	public If(Expr exp, Node thn, Node els) {
		this.exp = exp;
		this.thn = thn;
		this.els = els;
	}

	public If(Expr exp, Node thn) {
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

	public void accept(Visitor v) {
		v.visit(this);
	}

}
