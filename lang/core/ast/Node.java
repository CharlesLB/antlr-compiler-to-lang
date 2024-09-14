/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast;

import java.util.HashMap;

import lang.test.visitor.Visitable;

/**
 * Representa um nó da árvore sintática.
 */
public abstract class Node extends Visitable implements SuperNode {
    private int line, col;

    public Node(int l, int c) {
        line = l;
        col = c;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return col;
    }

    public abstract Object interpret(HashMap<String, Object> m);

}