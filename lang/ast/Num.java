package lang.ast;

/*
 * Esta classe representa um comando de Impressão.
 * Expr
 */
import java.util.HashMap;

public class Num extends Expr {

      private int l;

      public Num(int v) {
            this.l = v;
      }

      public int getValue() {
            return l;
      }

      // @Override
      public String toString() {
            return "" + l;
      }

      public int interpret(HashMap<String, Integer> m) {
            return l;
      }
}
