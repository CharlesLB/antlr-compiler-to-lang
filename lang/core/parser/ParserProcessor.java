package lang.core.parser;

import org.antlr.v4.runtime.*;
import lang.core.ast.*;

public class ParserProcessor {

    public static SuperNode getAstByTokens(CommonTokenStream tokens) throws Exception {
        LangParser parser = new LangParser(tokens);

        Node ast = parser.prog().ast;

        if (ast == null) {
            throw new Exception("AST is null");
        }

        return ast;
    }
}
