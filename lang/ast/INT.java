package lang.ast;

import java.util.HashMap;

public class INT extends Expr {

      private int value;

      public INT(int value) {
            this.value = value;
      }

      public int getValue() {
            return value;
      }

      @Override
      public int interpret(HashMap<String, Integer> m) {
            return value;
      }

      @Override
      public String toString() {
            return Integer.toString(value);
      }
}