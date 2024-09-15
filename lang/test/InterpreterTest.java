package lang.test;

import java.io.File;
import java.util.HashMap;

import org.antlr.v4.runtime.*;

import lang.core.ast.SuperNode;
import lang.core.ast.definitions.Data;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.StmtList;
import lang.core.ast.symbols.DataTable;
import lang.core.ast.symbols.FunctionTable;
import lang.core.parser.LexerProcessor;
import lang.core.parser.ParserProcessor;
import lang.utils.Logger;

public class InterpreterTest extends Test {

    public InterpreterTest(String path) {
        super(path);
    }

    public void test(File file) throws Exception {
        CommonTokenStream tokens;
        SuperNode ast;

        try {
            tokens = LexerProcessor.getTokens(file);
        } catch (Exception e) {
            Logger.error("\n Lexer test failed: " + e.getMessage());
            throw e;
        }

        try {
            ast = ParserProcessor.parserByTokens(tokens);
        } catch (Exception e) {
            Logger.error("\n Parser test failed: " + e.getMessage());
            throw e;
        }

        try {
            this.interpreterRunner(ast);
        } catch (Exception e) {
            Logger.error("\n Interpreter test failed: " + e.getMessage());
            throw e;
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
    public void declareDatas(SuperNode ast, DataTable dataTable) {
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
    public void declareFunctions(SuperNode ast, FunctionTable functionTable) {
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

    public void interpreterRunner(SuperNode ast) {
        FunctionTable.resetInstance();
        FunctionTable functionTable = FunctionTable.getInstance();
        declareFunctions(ast, functionTable);

        DataTable.resetInstance();
        DataTable dataTable = DataTable.getInstance();
        declareDatas(ast, dataTable);

        Fun mainFunction = functionTable.getMainFunction();
        if (mainFunction == null) {
            throw new RuntimeException("Função main não definida.");
        }

        HashMap<String, Object> globalContext = new HashMap<>();

        mainFunction.interpret(globalContext);
    }

}
