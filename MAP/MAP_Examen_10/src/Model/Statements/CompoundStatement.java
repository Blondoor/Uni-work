package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements IStatement {
    private IStatement first, second;

    public CompoundStatement(IStatement first, IStatement second)
    {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst()
    {
        return this.first;
    }

    public IStatement getSecond()
    {
        return this.second;
    }

    @Override
    public String toString()
    {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        return second.typecheck(first.typecheck(typeEnvironment));
    }
}
