package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFileStatement implements IStatement {
    Expression expr;
    String var_name;

    public readFileStatement(Expression expr, String var_name)
    {
        this.expr = expr;
        this.var_name = var_name;
    }

    @Override
    public String toString()
    {
        return "readFile(varf,varc)";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        IDictionary<String, Value> table = state.getSymbolTable();
        if(table.isDefined(this.var_name))
        {
            Value result = this.expr.evaluate(table, state.getHeap());
            if(result.getType().equals(new IntType()))
            {
                Value evaluationValue;
                evaluationValue = this.expr.evaluate(table, state.getHeap());
                if(evaluationValue.getType().equals(new StringType()))
                {
                    StringValue downcastedValue = (StringValue) evaluationValue;
                    //String exprValue = downcastedValue.getValue();
                    if(state.getFileTable().isDefined(downcastedValue.getValue()))
                    {
                        BufferedReader fileDescriptor = state.getFileTable().lookup(downcastedValue.getValue());
                        String line = fileDescriptor.readLine();
                        IntValue readValue;
                        if(line == null)
                            readValue = new IntValue(0);
                        else
                            readValue = new IntValue(Integer.parseInt(line));
                        state.getSymbolTable().update(var_name,readValue);
                    }
                    else throw new MyException("File doesn't exist");

                }
                else throw new MyException("Expression is not an int");
            }
            else throw new MyException("Type is not int");
        }
        else throw new MyException("var_name is not defined");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type varType = typeEnvironment.lookup(var_name);
        Type exprType = expr.typecheck(typeEnvironment);
        if(varType.equals(new IntType()))
            if(exprType.equals(new StringType()))
            {
                return typeEnvironment;
            }
            else throw new MyException("Read file statement - expression type is not a String");
        else throw new MyException("Read file statement - variable is not int");
    }
}
