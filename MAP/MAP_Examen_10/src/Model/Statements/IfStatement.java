package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStatement implements IStatement {
    Expression expr;
    IStatement stmt1, stmt2;

    public IfStatement(Expression expr, IStatement stmt1, IStatement stmt2)
    {
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public String toString()
    {
        return "if " + this.expr.toString() + "then " + this.stmt1.toString() + "else " + this.stmt2.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        Value result = this.expr.evaluate(state.getSymbolTable(), state.getHeap());
        if(((BoolValue) result).getValue())
            this.stmt1.execute(state);
        else
            this.stmt2.execute(state);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type ExpressionType = expr.typecheck(typeEnvironment);

        if(ExpressionType.equals(new BoolType()))
        {
            stmt1.typecheck(typeEnvironment);
            stmt2.typecheck(typeEnvironment);
            return typeEnvironment.clone();
        }
        else throw new MyException("Condition of IF is not bool type");
    }
}
