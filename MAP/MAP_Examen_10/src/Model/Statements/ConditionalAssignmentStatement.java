package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.PrgStmt.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class ConditionalAssignmentStatement implements IStatement {
    private String var;
    private Expression condition;
    private Expression trueCondition;
    private Expression falseCondition;

    public ConditionalAssignmentStatement(String var, Expression condition, Expression trueCondition, Expression falseCondition)
    {
        this.var = var;
        this.condition = condition;
        this.trueCondition = trueCondition;
        this.falseCondition = falseCondition;
    }

    @Override
    public String toString()
    {
        return var + "=" + condition.toString() + " ? " + trueCondition.toString() + ": " + falseCondition.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException
    {
        IStack<IStatement> execStack = state.getExecutionStack();
        IStatement newStatement = new IfStatement(condition, new AssignStatement(var, trueCondition), new AssignStatement(var, falseCondition));

        execStack.push(newStatement);
        state.setExecutionStack(execStack);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws MyException
    {
        Type conditionType = condition.typecheck(typeEnvironment);
        Type trueConditionType = trueCondition.typecheck(typeEnvironment);
        Type falseConditoonType = falseCondition.typecheck(typeEnvironment);
        Type typeVar = typeEnvironment.lookup(var);

        if(!conditionType.equals(new BoolType()))
        {
            if(trueConditionType.equals(falseConditoonType) && typeVar.equals(trueConditionType))
                return typeEnvironment;
            else throw new MyException("The variable, true condition and false condition are not of the same type");
        } else throw new MyException("Condition type is not bool");
    }
}
