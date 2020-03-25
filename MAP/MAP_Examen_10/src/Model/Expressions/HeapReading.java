package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class HeapReading implements Expression {
    private Expression expression;

    public HeapReading(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "rH(" + this.expression.toString() + ")";
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException
    {
        Value val = this.expression.evaluate(table, heap);
        if(val instanceof RefValue)
        {
            RefValue toref = (RefValue) val;
            int address = toref.getAddress();
            Value valueFromHeap = heap.get(address);

            if (valueFromHeap != null)
                return valueFromHeap;
            else throw new MyException("Address doesnt have a value");
        }
        else throw new MyException("Value is not RefValue");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type type = expression.typecheck(typeEnvironment);

        if(type instanceof RefType)
            return ((RefType) type).getInner();
        else throw new MyException("Read heap argument is not RefType");
    }
}
