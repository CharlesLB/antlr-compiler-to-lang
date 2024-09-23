/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */

package lang.enums;

public enum Mode {
    LEXICAL("lexer"),
    SINTATIC("parser"),
    SEMANTIC("semantic"),
    INTERPRETER("interpreter");

    private final String value;

    Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
