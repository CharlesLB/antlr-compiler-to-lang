package lang.ast;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/* Utilizada para representar estruturas do tipo: 
 * data Point {
 * 	x :: Int -> Decl.java
 * }
*/
public class Data extends Node {
	private ID id;
	private List<Decl> declarations;

	public Data(int l, int c, ID id, List<Decl> declarations) {
		super(l, c);
		this.id = id;
		this.declarations = declarations;
	}

	public Data(int l, int c) {
		super(l, c);
	}

	public ID getID() {
		return id;
	}

	public List<Decl> getDeclarations() {
		return declarations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("data ").append(id.getName()).append(" {\n");
		for (Decl decl : declarations) {
			sb.append(" ").append(decl.toString()).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		// Para cada declaração dentro da estrutura, interpretamos o decl
		for (Decl decl : declarations) {
			decl.interpret(context);
		}
		// Como `Data` não retorna um valor numérico direto, podemos retornar o
		// número
		// de declarações
		return declarations.size(); // Passo duvidoso
	}

}

// public class Data extends Node {
// private String name;
// private List<Decl> declarations;

// public Data(int l, int c, String name, List<Decl> declarations) {
// super(l, c);
// this.name = name;
// this.declarations = new ArrayList<>();
// }

// public Data(int l, int c) {
// super(l, c);
// }

// public void addDeclaration(Decl decl) {
// this.declarations.add(decl);
// }

// public String getName() {
// return name;
// }

// public List<Decl> getDeclarations() {
// return declarations;
// }

// @Override
// public String toString() {
// StringBuilder sb = new StringBuilder();
// sb.append("data ").append(name).append(" {\n");
// for (Decl decl : declarations) {
// sb.append(" ").append(decl.toString()).append("\n");
// }
// sb.append("}");
// return sb.toString();
// }

// @Override
// public int interpret(HashMap<String, Integer> context) {
// // Para cada declaração dentro da estrutura, interpretamos o decl
// for (Decl decl : declarations) {
// decl.interpret(context);
// }
// // Como `Data` não retorna um valor numérico direto, podemos retornar o
// número
// // de declarações
// return declarations.size(); // Passo duvidoso
// }

// }
