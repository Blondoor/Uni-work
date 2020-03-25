package View;/*package View;

import Controller.Controller;
import Domain.Values.*;
import Domain.Types.*;
import Domain.Expressions.*;
import Domain.PrgStmt.ProgramState;
import Domain.ADTs.*;
import Domain.Exceptions.*;
import Domain.Statements.*;

import java.util.Scanner;

public class View {
    private Controller cntrl;
    private Scanner KB = new Scanner(System.in);
    private IStatement s1 = new CompoundStatement(new VarDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
    private IStatement s2 = new CompoundStatement( new VarDeclarationStatement("a",new IntType()),  new CompoundStatement(new VarDeclarationStatement("b",new IntType()),
            new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
                    new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                    new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
    private IStatement s3 = new CompoundStatement(new VarDeclarationStatement("a",new BoolType()), new CompoundStatement(new VarDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true)))
            , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
    private ProgramState prgstate;
    private int printFlag;

    public View(Controller cntrl)
    {
        this.cntrl = cntrl;
        this.printFlag = 1;
    }

    private void initProgram(IStatement statement)
    {
        IStack<IStatement> exeStack = new MyStack<IStatement>();
        IDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
        List<Value> output = new MyList<Value>();
        this.prgstate = new ProgramState(exeStack, symbolTable, output, statement);
        this.cntrl.addProgram(this.prgstate);
    }

    private void ChooseProgram() throws MyException
    {
        System.out.println("Available programs:\n" +
                "Press: 1 for 'int v;v=2;print(v)'\n" +
                "2 for 'int a;int b; a=2+3*5;b=a+1;Print(b)'\n" +
                "3 for 'bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)'");

        int option;

        try
        {
            option = Integer.parseInt(this.KB.nextLine());
            switch (option)
            {
                case 1:
                    this.initProgram(s1);
                    break;
                case 2:
                    this.initProgram(s2);
                    break;
                case 3:
                    this.initProgram(s3);
                    break;
                default:
                    throw new MyException("Wrong command");
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void ChooseExecMode() throws MyException
    {
        System.out.println("1 for one-step\n" +
                "2 for all steps\n" +
                "3 to change the print flag  (Print flag is currently set to" + this.printFlag+")\n" +
                "0 to exit");

        int option;
        try
        {
            option = Integer.parseInt(this.KB.nextLine());
            switch (option)
            {
                case 1:
                    this.cntrl.executeOneStep(this.prgstate);
                    break;
                case 2:
                    this.cntrl.executeAllSteps();
                    throw new MyException("Exit");
                case 3:
                    this.printFlag = 1 - this.printFlag;
                    break;
                case 0:
                    throw new MyException("Exit");
                default:
                    throw new MyException("Wrong command");
            }
        }
        catch(MyException e)
        {
            throw e;
        }
    }

    public void run()
    {
        try
        {
            ChooseProgram();
        }
        catch(MyException e)
        {
            System.out.println(e.getMessage());
            return;
        }
        while(true)
        {
            try
            {
                ChooseExecMode();
            }
            catch (MyException e)
            {
                System.out.println(e.getMessage());
                if(e.getMessage().equals("Exit"))
                    break;
            }
        }
    }
}*/
