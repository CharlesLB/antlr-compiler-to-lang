package lang.test.visitor;

import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.StmtList;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.Print;
import lang.core.ast.statements.commands.ReadLValue;

public abstract class Visitor {
    public abstract void visit(Fun p);

    public abstract void visit(Visitable p);

    public abstract void visit(StmtList stmtList);

    public abstract void visit(Assign p);

    // public abstract void visit(ReadLValue readLValue);

    public abstract void visit(Print print);

}
