package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

import java.io.IOException;

public class HeapAllocation implements IStatement {
    private String var_name;
    private Expression expression;

    public HeapAllocation(String var_name, Expression expression)
    {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "new(" + this.var_name.toString() + ", " + this.expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        if(state.getSymbolTable().isDefined(var_name))
        {
            Value result = state.getSymbolTable().lookup(var_name);
            if(result instanceof RefValue)
            {
                Value expressionValue = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
                if (expressionValue.getType().equals(((RefValue) result).getLocationType()))
                {
                    int location = state.getHeap().allocate(expressionValue);
                    state.getSymbolTable().update(var_name, new RefValue(location,expressionValue.getType()));

                } else throw new MyException("Type is not refType");
            }
            else throw new MyException("Value is not Reference type");
        }
        else throw new MyException("The variable is not defined");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type typevar = typeEnvironment.lookup(this.var_name);
        Type typeExpr = expression.typecheck(typeEnvironment);
        if(typevar.equals(new RefType(typeExpr)))
            return typeEnvironment;
        else throw new MyException("Right hand side and left hand side have different types");

    }
}
