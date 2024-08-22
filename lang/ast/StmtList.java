package lang.ast;

/*
 * Esta classe representa um comando de atribuição.
 * Stmt ; StmtList
 */
import java.util.HashMap;

public class StmtList extends Node {
     private Node cmd1;
     private Node cmd2;

     public StmtList(Node cmd1, Node cmd2) {
          this.cmd1 = cmd1;
          this.cmd2 = cmd2;
     }

     public StmtList(Node cmd1) {
          this(cmd1, null);
     }

     public Node getCmd1() {
          return cmd1;
     }

     public Node getCmd2() {
          return cmd2;
     }

     @Override
     public String toString() {
          if (cmd2 != null) {
               return cmd1.toString() + ";\n" + cmd2.toString();
          }
          return cmd1.toString();
     }

     @Override
     public int interpret(HashMap<String, Integer> m) {
          if (cmd1 == null) {
               System.out.println("cmd1 is null!");
               return -1;
          }
          cmd1.interpret(m);

          if (cmd2 != null) {
               return cmd2.interpret(m);
          }

          return 0; // Valor padrão se cmd2 for null
     }
}