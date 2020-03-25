package Model.Statements.LatchTable;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStatement {
    String var;
    Expression expression;

    @Override
    public String toString()
    {
        return "NewLatch( " + var + ", " + expression.toString();
    }

    public NewLatch(String var, Expression expression)
    {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value exprVal = expression.evaluate(state.getSymbolTable(), state.getHeap());
        int nr = ((IntValue)exprVal).getValue();
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        int addr = state.getLatchTable().getFreeAddress();
        state.getLatchTable().put(addr, nr);
        state.getSymbolTable().update(var, new IntValue(addr));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type typeExp = expression.typecheck(typeEnvironment);
        Type typeVar = typeEnvironment.lookup(var);
        if(typeExp.equals(typeVar))
            return typeEnvironment;
        else throw new MyException("The two tipes are not equal");
    }
}
