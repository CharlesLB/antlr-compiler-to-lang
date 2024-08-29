package lang.parser;

import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;
import lang.ast.*;
import lang.ast.definitions.Fun;
import lang.ast.definitions.StmtList;
import lang.parser.LangLexer;
import lang.parser.LangParser;
import lang.symbols.FunctionTable;

public class LangParserAdaptor implements ParseAdaptor {

    @Override
    public SuperNode parseFile(String path) {
        try {

            CharStream stream = CharStreams.fromFileName(path);

            System.out.println("Parsing file: " + path);

            LangLexer lexer = new LangLexer(stream);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingError.INSTANCE);

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();

            // for (Token token : tokens.getTokens()) {
            // System.out.println("Token: " + token.getType() + " " + token.getText());
            // }

            // System.out.println("\n\n\n");

            LangParser parser = new LangParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingError.INSTANCE);

            try {
                parser.setBuildParseTree(false);

                // for (int i = 0; i < tokens.size(); i++) {
                // Token token = tokens.get(i);
                // System.out.println("Token: " + token.getText() + " " + token.getType());
                // }

                // System.out.println(tree.toStringTree(parser));

                Node ast = parser.prog().ast;

                if (ast == null) {
                    System.err.println("Parsing failed for file: " + path);
                    return null;
                }
                System.out.println(ast);

                FunctionTable functionTable = FunctionTable.getInstance();
                declareFunctions(ast, functionTable);
                functionTable.print();

                // HashMap<String, Object> m = new HashMap<String, Object>();
                // Object result = ast.interpret(m);

                Fun mainFunction = functionTable.getFunction("main");
                if (mainFunction == null) {
                    throw new RuntimeException("Função main não definida.");
                }

                // Executa a função main
                HashMap<String, Object> globalContext = new HashMap<String, Object>();
                System.out.println("Chamando main...");
                mainFunction.interpret(globalContext);

                return ast;
            } catch (ParseCancellationException e) {
                System.err.println("Erro de parsing: " + e.getMessage());
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error parsing file: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }
    }

    public void declareFunctions(Node ast, FunctionTable functionTable) {
        if (ast instanceof StmtList) {
            StmtList stmtList = (StmtList) ast;

            // Verifica se cmd1 é uma função
            if (stmtList.getCmd1() instanceof Fun) {
                Fun function = (Fun) stmtList.getCmd1();
                functionTable.addFunction(function);
            }

            // Verifica se cmd2 é uma função, se cmd2 não for nulo
            if (stmtList.getCmd2() != null && stmtList.getCmd2() instanceof Fun) {
                Fun function = (Fun) stmtList.getCmd2();
                functionTable.addFunction(function);
            }

            // Recursivamente verifica se cmd1 ou cmd2 são outros StmtList
            if (stmtList.getCmd1() instanceof StmtList) {
                declareFunctions(stmtList.getCmd1(), functionTable);
            }

            if (stmtList.getCmd2() instanceof StmtList) {
                declareFunctions(stmtList.getCmd2(), functionTable);
            }
        }
    }
}
