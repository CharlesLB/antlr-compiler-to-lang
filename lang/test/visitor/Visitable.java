package lang.test.visitor;

public abstract class Visitable {
    public void accept(Visitor v) {
        v.visit(this);
    }
}
