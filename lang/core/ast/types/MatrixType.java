/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.types;

import java.util.HashMap;

import lang.core.ast.definitions.Type;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type ‘[’ ‘]’
 * 
 * @Example Int[]
 * @Example ID[]
 * 
 */
public class MatrixType extends Type {
    private Type baseType; // (ex: Int, Float, etc.)
    private int dimensions;

    public MatrixType(int l, int c, Type baseType, int dimensions) {
        super(l, c);
        this.baseType = baseType;
        this.dimensions = dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public Type getBaseType() {
        return baseType;
    }

    public int getDimensions() {
        return dimensions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(baseType.toString());
        for (int i = 0; i < dimensions; i++) {
            sb.append("[]");
        }

        return sb.toString();
    }

    @Override
    public Object interpret(HashMap<String, Object> context) {
        return context.get(baseType);
    }

    public void accept(Visitor v) {
        try {
            v.visit(this);
        } catch (TypeMismatchException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}