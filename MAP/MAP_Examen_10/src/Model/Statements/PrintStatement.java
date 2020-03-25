package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.List;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

import java.io.IOException;


public class PrintStatement implements IStatement {
    private Expression expr;

    public PrintStatement(Expression expr)
    {
        this.expr = expr;
    }

    @Override
    public String toString()
    {
        return "print(" + this.expr.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        List<Value> output = state.getOutput();
        output.add(expr.evaluate(state.getSymbolTable(), state.getHeap()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        expr.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
