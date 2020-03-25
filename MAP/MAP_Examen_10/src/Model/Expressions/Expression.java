package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException;
    Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException;

    @Override
    String toString();
}
