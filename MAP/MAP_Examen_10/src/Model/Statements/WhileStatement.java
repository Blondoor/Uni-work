package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class WhileStatement implements IStatement {
    Expression expression;
    IStatement statement;

    public WhileStatement(Expression expression, IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public String toString()
    {
        return "While(" + expression.toString() + ")" + "{" + statement.toString() + "}";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException , IOException
    {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value result = expression.evaluate(symbolTable, state.getHeap());

        if(result.getType().equals(new BoolType()))
        {
            BoolValue dcValue = (BoolValue) result;
            if(dcValue.getValue())
            {
                state.getExecutionStack().push(new WhileStatement(expression, statement));
                state.getExecutionStack().push(statement);
            }
        }
        else throw new MyException("Expression is not a bool");

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type ExpressionType = expression.typecheck(typeEnvironment);

        if(ExpressionType.equals(new BoolType()))
        {
            statement.typecheck(typeEnvironment);
            return typeEnvironment.clone();
        }
        else throw new MyException("Condition of IF is not bool type");
    }
}
