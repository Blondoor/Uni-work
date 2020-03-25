package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

public class ValueExpression implements Expression {
    Value val;

    public ValueExpression(Value val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return this.val.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value>table, IHeap<Value> heap) throws MyException
    {
        return val;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        return val.getType();
    }
}
