package lang.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import lang.utils.ThrowingError;

import java.io.File;

public class LexerProcessor {

    public static CommonTokenStream getTokens(File file) throws Exception {
        CharStream stream = CharStreams.fromFileName(file.getAbsolutePath());

        LangLexer lexer = new LangLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingError.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.fill();

        return tokens;
    }
}
