package lang.test.visitor;

import lang.core.ast.definitions.Fun;

public abstract class Visitor {
    public abstract void visit(Fun p);

    public abstract void visit(Visitable p);

}
