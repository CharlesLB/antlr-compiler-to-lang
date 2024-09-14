package lang.enums;

public enum Mode {
    LEXICAL("lexical"),
    SINTATIC("sintatic"),
    SEMANTIC("semantic"),
    INTERPRETER("interpreter"),
    GVZ("gvz");

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
