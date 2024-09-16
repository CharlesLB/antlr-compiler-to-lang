package lang.test.visitor;

import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.StmtList;

public abstract class Visitor {
    public abstract void visit(Fun p);

    public abstract void visit(Visitable p);

    public abstract void visit(StmtList stmtList);

}
