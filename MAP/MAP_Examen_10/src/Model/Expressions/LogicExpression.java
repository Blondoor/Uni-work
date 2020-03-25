package Model.Expressions;

import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.ADTs.IDictionary;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.Types.BoolType;

public class LogicExpression implements Expression{
    Expression expr1, expr2;
    String op;

    public LogicExpression(String op, Expression expr1, Expression expr2)
    {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public String toString()
    {
        return this.expr1.toString() + " " + this.op + " " + this.expr2.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value>table, IHeap<Value> heap) throws MyException
    {
        Value v1, v2;
        v1 = expr1.evaluate(table, heap);
        if(v1.getType().equals(new BoolType()))
        {
            v2 = expr2.evaluate(table, heap);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                boolean n1, n2;

                n1 = b1.getValue();
                n2 = b2.getValue();

                if(op.equals("and"))
                    return new BoolValue(n1 && n2);
                if(op.equals("or"))
                    return new BoolValue(n1 || n2);
                else throw new MyException("Invalid operand");
            }
            else throw new MyException("Operand is not boolean type");
        }
        else throw new MyException("Operand is not boolean type");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type type1, type2;
        type1 = expr1.typecheck(typeEnvironment);
        type2 = expr2.typecheck(typeEnvironment);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else throw new MyException("Second operand is not boolean");
        }
        else throw new MyException("First operand is not boolean");
    }
}
