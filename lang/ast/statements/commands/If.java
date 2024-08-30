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

	public Object interpret(HashMap<String, Object> context) {
		Object conditionResult = exp.interpret(context);

		System.out.println("--- Entrando no IF --- " + conditionResult);

		if (conditionResult instanceof Boolean) {
			if ((Boolean) conditionResult) {
				System.out.println("--- Entrando no THEN --- " + thn);
				Object thenResult = thn.interpret(context);
				System.out.println("--- Resultado THEN --- " + thenResult);
				return thenResult;
			} else if (els != null) {
				System.out.println("--- Entrando no ELSE --- " + els);
				Object elseResult = els.interpret(context);
				System.out.println("--- Resultado ELSE --- " + elseResult);
				return elseResult;
			}
		} else {
			throw new RuntimeException("Unsupported type for condition in if statement");
		}

		return null;
	}

}
