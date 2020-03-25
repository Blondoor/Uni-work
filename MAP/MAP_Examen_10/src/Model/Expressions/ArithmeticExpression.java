package Model.Expressions;

import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.ADTs.IDictionary;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

public class ArithmeticExpression implements Expression {
    private Expression expr1, expr2;
    private char op;// 1- +, 2- -, 3- *, 4- /

    public ArithmeticExpression(char op, Expression expr1, Expression expr2)
    {
        this.op = op;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String toString()
    {
        return this.expr1.toString() + this.op + this.expr2.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value>table, IHeap<Value> heap) throws MyException
    {
        Value val1, val2;
        val1 = expr1.evaluate(table, heap);

        if(val1.getType().equals(new IntType()))
        {
            val2 = expr2.evaluate(table, heap);
            if (val2.getType().equals(new IntType()))
            {
                IntValue toInt1 = (IntValue)val1;
                IntValue toInt2 = (IntValue)val2;
                int n1, n2;
                n1 = toInt1.getValue();
                n2 = toInt2.getValue();
                switch (op)
                {
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if(n2 == 0)
                            throw new MyException("Division by zero");
                        return new IntValue(n1 / n2);
                    default:
                        throw new MyException("Invalid Operand");
                }
            }
            else throw new MyException("Operand is not an int");
        }
        else throw new MyException("Operand is not an int");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type type1, type2;
        type1 = expr1.typecheck(typeEnvironment);
        type2 = expr2.typecheck(typeEnvironment);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
            {
                return new IntType();
            }
            else throw new MyException("Secont operand is not an integer");
        }
        else throw new MyException("First operand is not an integer");
    }
}
