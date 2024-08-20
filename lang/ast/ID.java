package lang.ast;

/*
 * Esta classe representa um comando de Impressão.
 * Expr
 */

import java.util.HashMap;

public class ID extends Expr {

    private String l;

    public ID(String name) {
        this.l = name;
    }

    public String getName() {
        return l;
    }

    // @Override
    public String toString() {
        return l;
    }

    public int interpret(HashMap<String, Integer> m) {
        return m.get(l);
    }
}
