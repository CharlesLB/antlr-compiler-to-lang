/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.parser;

import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import lang.ast.*;
import lang.ast.definitions.Data;
import lang.ast.definitions.Fun;
import lang.ast.definitions.StmtList;
import lang.ast.symbols.DataTable;
import lang.ast.symbols.FunctionTable;

public class LangParserAdaptor implements ParseAdaptor {

    @Override
    public SuperNode parseFile(String path) {
        try {
            CharStream stream = CharStreams.fromFileName(path);

            LangLexer lexer = new LangLexer(stream);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingError.INSTANCE);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            LangParser parser = new LangParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingError.INSTANCE);

            try {
                parser.setBuildParseTree(false);

                Node ast = parser.prog().ast;

                if (ast == null) {
                    System.err.println("Parsing failed for file: " + path);
                    return null;
                }

                interpreterRunner(ast);

                return ast;
            } catch (ParseCancellationException e) {
                System.err.println("Erro de parsing: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error parsing file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Percorre a árvore de sintaxe abstrata (AST) a partir de um nó raiz e adiciona
     * todas as definições de estruturas de dados encontradas à tabela de dados
     * (`DataTable`).
     *
     * A função verifica se os comandos dentro de um `StmtList` (lista de comandos)
     * são instâncias de `Data`, representando definições de estruturas de dados, e
     * as adiciona
     * à `DataTable`. Se um comando for outra `StmtList`, a função é chamada
     * recursivamente
     * para continuar a busca por definições de estruturas de dados.
     */
    public void declareDatas(Node ast, DataTable dataTable) {
        if (ast instanceof StmtList) {
            StmtList stmtList = (StmtList) ast;

            if (stmtList.getCmd1() instanceof Data) {
                Data data = (Data) stmtList.getCmd1();
                dataTable.addData(data);
            }

            if (stmtList.getCmd2() != null && stmtList.getCmd2() instanceof Data) {
                Data data = (Data) stmtList.getCmd2();
                dataTable.addData(data);
            }

            if (stmtList.getCmd1() instanceof StmtList) {
                declareDatas(stmtList.getCmd1(), dataTable);
            }

            if (stmtList.getCmd2() instanceof StmtList) {
                declareDatas(stmtList.getCmd2(), dataTable);
            }
        }
    }

    /**
     * Percorre a árvore de sintaxe abstrata (AST) a partir de um nó raiz e adiciona
     * todas as definições de funções encontradas à tabela de funções
     * (`FunctionTable`).
     *
     * A função verifica se os comandos dentro de um `StmtList` (lista de comandos)
     * são instâncias de `Fun`, representando definições de funções, e as adiciona
     * à `FunctionTable`. Se um comando for outra `StmtList`, a função é chamada
     * recursivamente para continuar a busca por definições de funções.
     */
    public void declareFunctions(Node ast, FunctionTable functionTable) {
        if (ast instanceof StmtList) {
            StmtList stmtList = (StmtList) ast;

            if (stmtList.getCmd1() instanceof Fun) {
                Fun function = (Fun) stmtList.getCmd1();
                functionTable.addFunction(function);
            }

            if (stmtList.getCmd2() != null && stmtList.getCmd2() instanceof Fun) {
                Fun function = (Fun) stmtList.getCmd2();
                functionTable.addFunction(function);
            }

            if (stmtList.getCmd1() instanceof StmtList) {
                declareFunctions(stmtList.getCmd1(), functionTable);
            }

            if (stmtList.getCmd2() instanceof StmtList) {
                declareFunctions(stmtList.getCmd2(), functionTable);
            }
        }
    }

    public void interpreterRunner(Node ast) {
        FunctionTable.resetInstance();
        FunctionTable functionTable = FunctionTable.getInstance();
        declareFunctions(ast, functionTable);
        // functionTable.print();

        DataTable.resetInstance();
        DataTable dataTable = DataTable.getInstance();
        declareDatas(ast, dataTable);
        // dataTable.print();

        Fun mainFunction = functionTable.getMainFunction();
        if (mainFunction == null) {
            throw new RuntimeException("Função main não definida.");
        }

        HashMap<String, Object> globalContext = new HashMap<>();
        mainFunction.interpret(globalContext);
    }
}
