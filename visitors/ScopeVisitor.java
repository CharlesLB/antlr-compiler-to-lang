package visitors;

import lang.ast.*;
import lang.ast.definitions.Data;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.ast.expressions.ID;
import lang.ast.statements.commands.Assign;
import util.*;

public class ScopeVisitor extends Visitor {

	private ScopeTable scopes;
	private int level;

	public ScopeVisitor() {
		scopes = new ScopeTable();
		level = scopes.getLevel();
	}

	public void visit(Node p) {

	}

	public void visit(Param p) {

	}

	public void visit(ID p) {

	}

	public void visit(Fun p) {

	}

	public void visit(Data p) {

	}

	public void visit(Assign p) {

	}
}
