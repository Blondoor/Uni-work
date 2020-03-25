package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Types.Type;

public class NoStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state)
    {
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }
}
