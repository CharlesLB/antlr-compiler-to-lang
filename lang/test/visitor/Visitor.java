package lang.test.visitor;

import lang.core.ast.*;
import lang.core.ast.definitions.Data;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.expressions.ID;
import lang.core.ast.statements.commands.Assign;

public abstract class Visitor {
    public abstract void visit(Node p);

    public abstract void visit(Param p);

    public abstract void visit(ID p);

    public abstract void visit(Fun p);

    public abstract void visit(Data p);

    public abstract void visit(Assign p);
}
