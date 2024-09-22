/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Symbol> map;

    public SymbolTable() {
        map = new HashMap<String, Symbol>();
    }

    public void put(String key, Symbol value) {
        map.put(key, value);
    }

    public Symbol lookup(String key) {
        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append(" -> ").append(map.get(key).toString()).append("\n");
        }
        return sb.toString();
    }
}
