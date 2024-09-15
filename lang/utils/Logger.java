package lang.utils;

public final class Logger {
    public static boolean verbose = false;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void setVerbose(boolean verbose) {
        Logger.verbose = verbose;
    }

    public static void log(String message) {
        System.out.print(message);
    }

    public static void success(String message) {
        if (verbose) {
            System.out.print(ANSI_GREEN + message + ANSI_RESET);
        }
    }

    public static void info(String message) {
        if (verbose) {
            System.out.print(ANSI_BLUE + message + ANSI_RESET);
        }
    }

    public static void warning(String message) {
        if (verbose) {
            System.out.print(ANSI_YELLOW + message + ANSI_RESET);
        }
    }

    public static void error(String message) {
        if (verbose) {
            System.err.print(ANSI_RED + message + ANSI_RESET);
        }
    }

}
