package lang.test.visitor;

import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.definitions.StmtList;
import lang.core.ast.definitions.Type;
import lang.core.ast.expressions.BinOP;
import lang.core.ast.expressions.FunCallWithIndex;
import lang.core.ast.expressions.ID;
import lang.core.ast.expressions.literals.BoolLiteral;
import lang.core.ast.expressions.literals.CharLiteral;
import lang.core.ast.expressions.literals.FloatLiteral;
import lang.core.ast.expressions.literals.IntLiteral;
import lang.core.ast.expressions.literals.NullLiteral;
import lang.core.ast.expressions.operators.And;
import lang.core.ast.expressions.operators.Div;
import lang.core.ast.expressions.operators.EQ;
import lang.core.ast.expressions.operators.LessThan;
import lang.core.ast.expressions.operators.Minus;
import lang.core.ast.expressions.operators.Mod;
import lang.core.ast.expressions.operators.Mul;
import lang.core.ast.expressions.operators.Neg;
import lang.core.ast.expressions.operators.Not;
import lang.core.ast.expressions.operators.NotEq;
import lang.core.ast.expressions.operators.Plus;
import lang.core.ast.lvalue.ArrayAccessLValue;
import lang.core.ast.lvalue.FunLValue;
import lang.core.ast.lvalue.IDLValue;
import lang.core.ast.lvalue.LValue;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.BlockCmd;
import lang.core.ast.statements.commands.If;
import lang.core.ast.statements.commands.Iterate;
import lang.core.ast.statements.commands.Print;
import lang.core.ast.statements.commands.ReadLValue;
import lang.core.ast.statements.commands.Return;
import lang.test.visitor.symbols.TypeSymbol;

public abstract class Visitor {
    // Métodos abstratos para visitar cada tipo de nó
    public abstract void visit(Fun p);

    public abstract void visit(Param param);

    public abstract TypeSymbol visit(Type type);

    public abstract void visit(StmtList stmtList);

    public abstract void visit(Assign p);

    public abstract void visit(ReadLValue readLValue);

    public abstract void visit(Print print);

    public abstract void visit(Return returnCmd);

    public abstract void visit(If ifStmt);

    public abstract void visit(BlockCmd blockCmd);

    public abstract void visit(Iterate iterateCmd);

    // Métodos abstratos para visitar expressões
    public abstract TypeSymbol visit(BinOP binOp);

    public abstract TypeSymbol visit(FunCallWithIndex funCallWithIndex);

    public abstract void visit(FunLValue funLValue);

    // public abstract void visit(ID id);

    public abstract TypeSymbol visit(BoolLiteral boolLiteral);

    public abstract TypeSymbol visit(CharLiteral charLiteral);

    public abstract TypeSymbol visit(FloatLiteral floatLiteral);

    public abstract TypeSymbol visit(IntLiteral intLiteral);

    public abstract TypeSymbol visit(NullLiteral nullLiteral);

    // Métodos abstratos para operadores
    public abstract TypeSymbol visit(Not not);

    public abstract TypeSymbol visit(Neg neg);

    // Métodos abstratos para lvalues
    // public abstract TypeSymbol visit(LValue lvalue);

    public abstract TypeSymbol visit(IDLValue idlValue);

    // public abstract void visit(ArrayAccessLValue arrayAccessLValue);

    // Método genérico para visitar qualquer nó
    public abstract void visit(Visitable p);

}
