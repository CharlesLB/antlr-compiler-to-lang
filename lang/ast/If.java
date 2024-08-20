package lang.ast;

/*
 * Esta classe representa uma expressão de soma.
 * Expr + Expr
 */

import java.util.HashMap;

public class If extends Node {

    private Expr teste;
    private Node thn;
    private Node els;

    public If(Expr teste, Node thn, Node els) {
        this.teste = teste;
        this.thn = thn;
        this.els = els;
    }

    public If(Expr teste, Node thn) {
        this.teste = teste;
        this.thn = thn;
        this.els = null;
    }

    public String toString() {
        String s = teste.toString();
        String sthn = thn.toString();
        String sels = els != null ? " : " + els.toString() : "";
        s += " ? [" + sthn + sels + "]";
        return s.replace('\n', '\0');
    }

    public int interpret(HashMap<String, Integer> m) {
        int n = teste.interpret(m);
        if (n != 0) {
            return thn.interpret(m);
        } else if (els != null) {
            return els.interpret(m);
        }
        return n;
    }

}
