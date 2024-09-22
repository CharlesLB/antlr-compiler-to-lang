package lang.core.parser;

import org.antlr.v4.runtime.*;
import lang.core.ast.*;
import lang.core.ast.definitions.Fun;
import lang.test.visitor.ContextVisitor;

public class SemanticProcessor {

    public static void semanticTest(Node ast) throws Exception {
        if (ast == null) {
            throw new Exception("AST is null");
        }

        ast.accept(new ContextVisitor());
    }
}
