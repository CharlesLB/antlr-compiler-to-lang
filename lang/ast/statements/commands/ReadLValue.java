/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import lang.ast.definitions.Cmd;
import lang.ast.lvalue.LValue;
import visitors.Visitor;

/**
 * Representa um comando de leitura de um LValue.
 * 
 * @Parser read lvalue ‘;’
 * 
 * @Example read x;
 */
public class ReadLValue extends Cmd {
	private LValue lvalue;

	public ReadLValue(LValue lvalue) {
		this.lvalue = lvalue;
	}

	public LValue getLValue() {
		return lvalue;
	}

	@Override
	public String toString() {
		return "read " + lvalue.toString() + ";";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}