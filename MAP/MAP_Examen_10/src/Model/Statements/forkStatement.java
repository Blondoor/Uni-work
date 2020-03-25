package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class forkStatement implements IStatement {
    IStatement statement;

    public forkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public String toString()
    {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException
    {
        return new ProgramState(new MyStack<>(),
                                state.getSymbolTable().clone(),
                                state.getOutput(),
                                statement,
                                state.getFileTable(),
                                state.getHeap(),
                                state.getLatchTable());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        statement.typecheck(typeEnvironment.clone());
        return typeEnvironment;
    }
}
