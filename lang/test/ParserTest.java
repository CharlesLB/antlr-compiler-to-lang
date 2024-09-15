package lang.test;

import java.io.File;

import org.antlr.v4.runtime.CommonTokenStream;

import lang.core.ast.SuperNode;
import lang.core.parser.LexerProcessor;
import lang.core.parser.ParserProcessor;
import lang.utils.Logger;

public class ParserTest extends Test {

    public ParserTest(String path) {
        super(path);
    }

    public void test(File file) throws Exception {
        CommonTokenStream tokens;

        try {
            tokens = LexerProcessor.getTokens(file);
        } catch (Exception e) {
            Logger.error("\n Lexer test failed: " + e.getMessage());
            throw e;
        }

        try {
            SuperNode ast = ParserProcessor.parserByTokens(tokens);

            if (Logger.verbose) {
                this._printAstTable(ast);
            }

        } catch (Exception e) {
            Logger.error("\n Parser test failed: " + e.getMessage());
            throw e;
        }
    }

    private void _printAstTable(SuperNode ast) {
        System.out.println(ast.toString());
    }
}
