package lang.ast.definitions;

import java.util.*;

import lang.ast.Node;
import lang.ast.expressions.ID;
import lang.ast.statements.data.Decl;
import lang.symbols.DataTable;

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

	public Set<String> getAttributes() {
		Set<String> attributes = new HashSet<>();
		for (Decl decl : declarations) {
			attributes.add(decl.getID().getName());
		}
		return attributes;
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
	public Object interpret(HashMap<String, Object> context) {
		HashMap<String, Object> localContext = new HashMap<String, Object>();

		for (Decl decl : declarations) {
			localContext.put(decl.getID().getName(), decl.getType());
		}

		context.put(id.getName(), localContext);

		return context;
	}

}