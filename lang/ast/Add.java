package lang.ast;

/*
 * Esta classe representa uma expressão de soma.
 * Expr + Expr
 */

import java.util.HashMap;

public class Add extends BinOP {

   public Add(Expr l, Expr r) {
      super(l, r);
   }

   public String toString() {
      String s = getLeft().toString();
      String ss = getRight().toString();
      if (getRight() instanceof Add) {
         ss = "(" + ss + ")";
      }
      return s + " + " + ss;
   }

   public int interpret(HashMap<String, Integer> m) {
      return getLeft().interpret(m) + getRight().interpret(m);
   }

}
