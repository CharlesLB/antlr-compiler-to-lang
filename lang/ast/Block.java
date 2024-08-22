// package lang.ast;

// import java.util.HashMap;

// public class Block extends Node {
// 	private StmtList statements;

// 	public Block(StmtList statements) {
// 		this.statements = statements;
// 	}

// 	public StmtList getStatements() {
// 		return statements;
// 	}

// 	@Override
// 	public String toString() {
// 		return "{\n" + statements.toString() + "\n}";
// 	}

// 	@Override
// 	public int interpret(HashMap<String, Integer> m) {
// 		return statements.interpret(m);
// 	}
// }
