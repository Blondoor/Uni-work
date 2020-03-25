package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.PrgStmt.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.time.chrono.IsoChronology;
import java.util.concurrent.CopyOnWriteArrayList;


public class Interpreter {
    public static void main(String[] args)
    {
        IStatement s1 = new CompoundStatement(
                new VarDeclarationStatement("v",new IntType()), new CompoundStatement(
                        new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(
                                new VariableExpression("v"))));
        IRepository repo1 = new Repository("log1.txt");
        Controller ctrl1 = new Controller(repo1);
        ProgramState prg1 = new ProgramState(s1);
        ctrl1.addProgram(prg1);

        IStatement s2 = new CompoundStatement(
                new VarDeclarationStatement("a",new IntType()),  new CompoundStatement(new VarDeclarationStatement("b",new IntType()),
                new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(
                        new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5))))),  new CompoundStatement(
                                new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                                new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IRepository repo2 = new Repository("log2.txt");
        Controller ctrl2 = new Controller(repo2);
        ProgramState prg2 = new ProgramState(s2);
        ctrl1.addProgram(prg2);

        IStatement s3 = new CompoundStatement(
                new VarDeclarationStatement("a",new BoolType()), new CompoundStatement(
                        new VarDeclarationStatement("v", new IntType()),new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),new AssignStatement("v",new ValueExpression(
                                                        new IntValue(2))), new AssignStatement("v", new ValueExpression(
                                                                new IntValue(3)))), new PrintStatement(
                                                                        new VariableExpression("v"))))));
        IRepository repo3 = new Repository("log3.txt");
        Controller ctrl3 = new Controller(repo3);
        ProgramState prg3 = new ProgramState(s3);
        ctrl2.addProgram(prg3);

        IStatement ex4 = new CompoundStatement(
                new VarDeclarationStatement("varf",new StringType()),new CompoundStatement(
                new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
                new OpenRFileStatement(new VariableExpression("varf")),new CompoundStatement(
                new VarDeclarationStatement("varc",new IntType()),new CompoundStatement(
                new readFileStatement(new VariableExpression("varf"),"varc"),new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
                new readFileStatement(new VariableExpression("varf"),"varc") ,new CompoundStatement(
                        new PrintStatement(new VariableExpression("varc")),new closeFileStatement(
                                new VariableExpression("varf"))))))))));
        IRepository repo4 = new Repository("log4.txt");
        Controller ctrl4 = new Controller(repo4);
        ProgramState prg4 = new ProgramState(ex4);
        ctrl4.addProgram(prg4);

        IStatement ex5 = new CompoundStatement(
          new VarDeclarationStatement("v", new IntType()),
          new CompoundStatement(
                  new AssignStatement("v", new ValueExpression(new IntValue(24))),
                  new WhileStatement(
                          new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(20)), ">"),
                          new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                  new AssignStatement("v",new ArithmeticExpression('-', new VariableExpression("v"),new ValueExpression(new IntValue(1)))))
                  )
          )
        );
        IRepository repo5 = new Repository("log5.txt");
        Controller ctrl5 = new Controller(repo5);
        ProgramState prg5 = new ProgramState(ex5);
        ctrl5.addProgram(prg5);

        IStatement ex6 = new CompoundStatement(
                new VarDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VarDeclarationStatement("a",new RefType(new RefType(new  IntType()))), new CompoundStatement(
                                new HeapAllocation("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapAllocation("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new HeapReading(new HeapReading( new VariableExpression("a")))))))));
        IRepository repo6 = new Repository("log6.txt");
        Controller ctrl6 = new Controller(repo6);
        ProgramState prg6 = new ProgramState(ex6);
        ctrl6.addProgram(prg6);

        IStatement ex7 = new CompoundStatement(
                new VarDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocation("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReading(new VariableExpression("v"))), new CompoundStatement(
                                new VarDeclarationStatement("a",new RefType(new RefType(new  IntType()))), new CompoundStatement(
                                new HeapAllocation("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapWriting("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReading(new HeapReading( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        IRepository repo7 = new Repository("log7.txt");
        Controller ctrl7 = new Controller(repo7);
        ProgramState prg7 = new ProgramState(ex7);
        ctrl7.addProgram(prg7);


        IStatement forked = new CompoundStatement(new HeapWriting("a",new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReading(new VariableExpression("a"))))));

        IStatement ex8 = new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VarDeclarationStatement("a",new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReading(new VariableExpression("a"))))))
                        )));
        IRepository repo8 = new Repository("log8.txt");
        Controller ctrl8 = new Controller(repo8);
        ProgramState prg8 = new ProgramState(ex8);
        ctrl8.addProgram(prg8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExample("1", s1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", s2.toString(),ctrl2));
        menu.addCommand(new RunExample("3", s3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctrl6));
        menu.addCommand(new RunExample("7", ex7.toString(),ctrl7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctrl8));
        menu.Show();
    }
}
