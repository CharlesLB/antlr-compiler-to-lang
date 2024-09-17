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
 * @Parser type
 * 
 * @Example ID
 * 
 * @info não respeita perfeitamente a gramática, uma vez que existem 3
 *       categorias de ID e, na gramática, 2;
 */
public class IDType extends Type {
    private String id;

    public IDType(int line, int column, String id) {
        super(line, column);
        this.id = id;
    }

    public String getName() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public Object interpret(HashMap<String, Object> context) {
        return context.get(id);
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