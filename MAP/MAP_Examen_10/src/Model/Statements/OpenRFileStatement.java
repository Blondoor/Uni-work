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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement {
    Expression exp;

    public OpenRFileStatement(Expression e)
    {
        this.exp = e;
    }

    @Override
    public String toString()
    {
        return "OpenRFile(varf)";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        IDictionary<String, Value> table = state.getSymbolTable();
        Value result = this.exp.evaluate(table, state.getHeap());

        if(result.getType().equals(new StringType()))
        {
            StringValue downcastValue = (StringValue)result;
            //String exprValue = downcastValue.getValue();
            if(!state.getFileTable().isDefined(downcastValue.getValue()))
            {
                BufferedReader fileDescriptor = null;
                try {
                    fileDescriptor = new BufferedReader(new FileReader((downcastValue).getValue()));
                    state.getFileTable().update(downcastValue.getValue(), fileDescriptor);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else throw new MyException("the file already exists");
        }
        else throw new MyException("The types don't match");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type exprType = exp.typecheck(typeEnvironment);

        if(exprType.equals(new StringType()))
        {
            return typeEnvironment;
        }
        else throw new MyException("Read statement - expression type is not a String");
    }
}
