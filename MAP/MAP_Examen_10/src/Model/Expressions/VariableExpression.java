package Model.Expressions;

import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.ADTs.IDictionary;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return this.id.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value>table, IHeap<Value> heap) throws MyException
    {
        return table.lookup(id);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        return typeEnvironment.lookup(id);
    }
}
