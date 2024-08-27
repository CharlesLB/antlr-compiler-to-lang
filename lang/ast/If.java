package lang.ast;

import java.util.HashMap;

public class If extends Node {

	private Expr teste;
	private Node thn;
	private Node els;

	public If(int lin, int col, Expr teste, Node thn, Node els) {
		super(lin, col);
		this.teste = teste;
		this.thn = thn;
		this.els = els;
	}

	public If(int lin, int col, Expr teste, Node thn) {
		super(lin, col);
		this.teste = teste;
		this.thn = thn;
		this.els = null;
	}

	public String toString() {
		String s = teste.toString();
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
		int n = teste.interpret(m);
		if (n != 0) {
			return thn.interpret(m);
		} else if (els != null) {
			return els.interpret(m);
		}
		return n;
	}

}
