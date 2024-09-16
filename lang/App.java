package lang;

import lang.enums.Mode;
import lang.test.InterpreterTest;
import lang.test.LexerTest;
import lang.test.ParserTest;
import lang.test.SemanticTest;
import lang.utils.Logger;

public class App {

    public static void main(String[] args) {
        System.out.println("\nWelcome to the Lang compiler! \n");

        if (args.length < 1) {
            printHelp();
            return;
        }

        try {
            String mode = args[0];
            String path = args[1];
            Boolean logs = args.length >= 3 ? true : false;

            if (logs) {
                Logger.setVerbose(true);
            }

            switch (mode) {
                case "lexer":
                    new LexerTest(path).run();
                    break;
                case "parser":
                    new ParserTest(path).run();
                    break;
                case "semantic":
                    new SemanticTest(path).run();
                    break;
                case "interpreter":
                    new InterpreterTest(path).run();
                    break;
                case "gvz":
                    // new GVZTest(file).run();
                    break;
                default:
                    printHelp();
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printHelp() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";

        System.out.println(ANSI_YELLOW + "use <make run MODE=... PATH=... LOGS?=...>" + ANSI_RESET);
        System.out.println("Exemplo: make run MODE=lexical FILE=example.lan \n");

        System.out.println(ANSI_BLUE + "Path can be a file or a dir." + ANSI_RESET + "\n");

        System.out.println(ANSI_GREEN + "PATH (caminho para o arquivo ou diretório): \n" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "MODE (uma das seguintes possibilidades):  \n" + ANSI_RESET);

        System.out.println(ANSI_YELLOW + Mode.LEXICAL + ANSI_RESET + ": Executa uma bateria de testes léxicos");
        System.out.println(ANSI_YELLOW + Mode.SINTATIC + ANSI_RESET + ": Executa uma bateria de testes sintáticos");
        System.out.println(ANSI_YELLOW + Mode.SEMANTIC + ANSI_RESET + ": Executa uma bateria de testes semânticos");
        System.out
                .println(ANSI_YELLOW + Mode.INTERPRETER + ANSI_RESET
                        + ": Executa uma bateria de testes no interpretador");

        System.out.println(ANSI_YELLOW + Mode.GVZ + ANSI_RESET
                + ": Create a dot file. (Feed it to graphviz dot tool to generate graphical representation of the AST)");

        System.out.println(ANSI_GREEN + "\nLOGS (opcional):" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "Se definido como true, exibirá logs de execução" + ANSI_RESET);

        System.out.println();

        return;
    }
}
