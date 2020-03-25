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
import java.lang.invoke.MutableCallSite;

public class HeapWriting implements IStatement
{
    private String var_name;
    private Expression expression;

    public HeapWriting(String var_name, Expression expression)
    {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "wH(" + this.var_name + ", " + this.expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        if(state.getSymbolTable().isDefined(var_name))
        {
            Value val = state.getSymbolTable().lookup(this.var_name);
            if(val instanceof RefValue)
            {
                int address = ((RefValue) val).getAddress();
                if(state.getHeap().get(address) != null)
                {
                    Value result = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
                    if(result.getType().equals(((RefValue) val).getLocationType()))
                    {
                        state.getHeap().put(address,result);
                    }
                    else throw new MyException("Incompatible types");
                }
                else throw new MyException("Address is not a key in heap");
            }
            else throw new MyException("Value is not RefType");
        }
        else throw new MyException("Variable not defined");

        return state;
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
