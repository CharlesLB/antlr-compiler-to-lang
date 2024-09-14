package lang;

import lang.core.parser.*;
import lang.core.ast.*;

public class LangCompiler {

    public static void main(String[] args) {
        System.out.println("\n Welcome to the Lang compiler! \n");

        if (args.length < 1) {
            printHelp();
            return;
        }

        try {
            LangParserAdaptor langParser = new LangParserAdaptor();

            if (args[0].equals("-bs")) {
                System.out.println("Executando bateria de testes sintáticos:");
                langParser.skipOnSintaticTest = true;

                new TestParser(langParser);
                return;
            }
            if (args[0].equals("-bsm")) {
                System.out.println("Executa uma bateria de testes no interpretador");
                new TestParser(langParser);
                return;
            }
            if (args.length != 2) {
                System.out.println("Para usar essa opção, especifique um nome de arquivo");
                return;
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

        System.out.println(ANSI_YELLOW + "use <make run MODE=... FILE=...>" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "MODE (uma das seguintes possibilidades):  \n" + ANSI_RESET);

        System.out.println(ANSI_YELLOW + "lexical" + ANSI_RESET + ": Executa uma bateria de testes léxicos");
        System.out.println(ANSI_YELLOW + "sintatic" + ANSI_RESET + ": Executa uma bateria de testes sintáticos");
        System.out.println(ANSI_YELLOW + "semantic" + ANSI_RESET + ": Executa uma bateria de testes semânticos");
        System.out
                .println(ANSI_YELLOW + "interpreter" + ANSI_RESET + ": Executa uma bateria de testes no interpretador");

        System.out.println(ANSI_YELLOW + "gvz" + ANSI_RESET
                + ": Create a dot file. (Feed it to graphviz dot tool to generate graphical representation of the AST)");

        System.out.println("\n" + ANSI_BLUE + "File is optional" + ANSI_RESET + "\n");

        System.out.println("Exemplo: make run MODE=lexical FILE=example.lan");

        return;
    }
}
