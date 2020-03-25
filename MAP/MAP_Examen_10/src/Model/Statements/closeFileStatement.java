package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeFileStatement implements IStatement {
    Expression expr;

    public closeFileStatement(Expression expr)
    {
        this.expr = expr;
    }

    @Override
    public String toString()
    {
        return "closeFile(expr)";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        Value evaluationValue;
        evaluationValue = this.expr.evaluate(state.getSymbolTable(), state.getHeap());
        if(evaluationValue.getType().equals(new StringType()))
        {
            StringValue downcastedValue = (StringValue) evaluationValue;
            if(state.getFileTable().isDefined(downcastedValue.getValue()))
            {
                BufferedReader associatedFileDescriptor = state.getFileTable().lookup(downcastedValue.getValue());
                associatedFileDescriptor.close();
                state.getFileTable().remove(downcastedValue.getValue());
            }
            else throw new MyException("File doesn't exist");
        }
        else throw new MyException("Type is not String");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type exprType = expr.typecheck(typeEnvironment);

        if(exprType.equals(new StringType()))
        {
            return typeEnvironment;
        }
        else throw new MyException("Close statement - expression type is not a String");
    }
}
