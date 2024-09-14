package lang.core.parser;

import java.io.*;
import lang.core.ast.SuperNode;
import lang.core.parser.*;
import java.util.List;

// Adaptador para classe de parser. a Função parseFile deve retornar null caso o parser resulte em erro. 

public interface ParseAdaptor {
   public abstract SuperNode parseFile(String path);

}
