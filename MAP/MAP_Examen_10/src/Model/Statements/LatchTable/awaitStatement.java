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

public class awaitStatement implements IStatement {
    private String var;

    @Override
    public String toString()
    {
        return "await(" + var + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type typeVar = typeEnvironment.lookup(var);
        if(typeVar.equals(new IntType()))
            return typeEnvironment;
        else throw new MyException("var type is not int");
    }

    public awaitStatement(String var)
    {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if(state.getSymbolTable().isDefined(var))
        {
            Value index = state.getSymbolTable().lookup(var);
            int foundIndex = ((IntValue)index).getValue();

            if (state.getLatchTable().isDefined(foundIndex))
            {
                if(state.getLatchTable().getLatchTable().get(foundIndex) != 0)
                    state.getExecutionStack().push(new awaitStatement(var));
            }else throw new MyException("Index is not in latchtable");
        }else throw new MyException("variable is not in symtable");
        lock.unlock();
        return null;
    }
}
