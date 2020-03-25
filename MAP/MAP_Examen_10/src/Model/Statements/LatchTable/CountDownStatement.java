package Model.Statements.LatchTable;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Statements.IStatement;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement implements IStatement {
    String var;

    public CountDownStatement(String var)
    {
        this.var = var;
    }

    @Override
    public String toString()
    {
        return "CountDown(" + var + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type typeVar = typeEnvironment.lookup(var);
        if(typeVar.equals(new IntType()))
            return typeEnvironment;
        else throw new MyException("var type is not int");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        Value index = state.getSymbolTable().lookup(var);
        int foundIndex = ((IntValue)index).getValue();
        if(state.getLatchTable().getLatchTable().get(foundIndex) > 0)
        {
            state.getLatchTable().getLatchTable().put(foundIndex, state.getLatchTable().getLatchTable().get(foundIndex) - 1);
            state.getOutput().add(new IntValue(state.getID()));
        }

        lock.unlock();
        return null;
    }
}
