package visitors;

import lang.ast.*;
import lang.ast.definitions.Data;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.ast.expressions.ID;
import lang.ast.statements.commands.Assign;

public abstract class Visitor {
	public abstract void visit(Node p);

	public abstract void visit(Param p);

	public abstract void visit(ID p);

	public abstract void visit(Fun p);

	public abstract void visit(Data p);

	public abstract void visit(Assign p);

}
