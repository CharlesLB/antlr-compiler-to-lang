package lang.ast;

/*
 * Esta classe representa uma expressão de soma.
 * Expr + Expr
 */

import java.util.HashMap;

public class Minus extends BinOP {

   public Minus(Expr l, Expr r) {
      super(l, r);
   }

   public String toString() {
      String s = getLeft().toString();
      String ss = getRight().toString();
      if (getRight() instanceof Minus) {
         ss = "(" + ss + ")";
      }
      return s + " - " + ss;
   }

   public int interpret(HashMap<String, Integer> m) {
      return getLeft().interpret(m) - getRight().interpret(m);
   }

}
