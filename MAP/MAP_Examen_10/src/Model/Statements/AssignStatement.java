package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.IOException;

public class AssignStatement implements IStatement {
    private String id;
    private Expression expr;

    public AssignStatement(String id, Expression expr)
    {
        this.expr = expr;
        this.id = id;
    }

    @Override
    public String toString()
    {
        return this.id + "=" + this.expr.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        IDictionary<String, Value> table = state.getSymbolTable();

        if(table.isDefined(this.id))
        {
            Value result = this.expr.evaluate(table, state.getHeap());

            if(result.getType().equals(table.lookup(id).getType()))
                table.update(this.id,result);
            else throw new MyException("Type of expression and type of variable don't match");
        }
        else throw new MyException("Variable type is not defined");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type typevar = typeEnvironment.lookup(id);
        Type typeExpression = expr.typecheck(typeEnvironment);
        if(typevar.equals(typeExpression))
        {
            return typeEnvironment;
        }
        else throw new MyException("Assignment: right hand side and left hand side have different types");
    }
}
