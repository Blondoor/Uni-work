package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.IOException;

public class VarDeclarationStatement implements IStatement {
    private String name;
    private Type type;

    public VarDeclarationStatement(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString()
    {
        return this.type.toString() + this.name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        IDictionary<String, Value> table = state.getSymbolTable();
        if(table.isDefined(this.name))
            throw new MyException("Variable already declared");
        else
            table.update(this.name,this.type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        typeEnvironment.update(name,type);
        return typeEnvironment;
    }
}
