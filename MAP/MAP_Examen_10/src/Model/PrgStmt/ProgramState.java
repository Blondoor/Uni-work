package Model.PrgStmt;

import Model.ADTs.*;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IDictionary<String, BufferedReader> FileTable;
    private List<Value> output;
    private IHeap<Value> heap;
    private ILatchTable<Integer, Integer> latchTable;
    private IStatement ogProgram;
    private int id;
    private static int globalid = 1;

    public ProgramState(IStatement originalProgram){
        this.executionStack = new MyStack<IStatement>();
        this.symbolTable = new MyDictionary<String,Value>();
        this.output = new MyList<Value>();
        this.FileTable = new MyDictionary<String,BufferedReader>();
        this.heap = new MyHeap<Value>();
        this.latchTable = new MyLatchTable<>();
        this.executionStack.push(originalProgram);
        id = 1;
    }

    public synchronized static int incrementID()
    {
        globalid = globalid * 10;
        return globalid;
    }

    public boolean isNotCompleted()
    {
        return executionStack.isEmpty()!=true;
    }

    public ProgramState oneStep() throws MyException, IOException {
        if(executionStack.isEmpty()) throw new MyException("Stack is empty");

        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public ILatchTable<Integer, Integer> getLatchTable()
    {
        return latchTable;
    }

    public void setLatchTable(ILatchTable<Integer, Integer> latchTable)
    {
        this.latchTable = latchTable;
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, List<Value> output, IStatement ogProgram, IDictionary<String, BufferedReader> FileTable, IHeap<Value> heap, ILatchTable<Integer, Integer> latchTable)
    {
        this.executionStack = executionStack;
        this.ogProgram = ogProgram;
        this.output = output;
        this.symbolTable = symbolTable;
        this.FileTable = FileTable;
        this.executionStack.push(this.ogProgram);
        this.heap = heap;
        this.latchTable = latchTable;
        this.id = incrementID();
    }

    public IDictionary<String, BufferedReader> getFileTable()
    {
        return this.FileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> Filetable)
    {
        this.FileTable = Filetable;
    }

    public IStack<IStatement> getExecutionStack()
    {
        return this.executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack)
    {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable()
    {
        return this.symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable)
    {
        this.symbolTable = symbolTable;
    }

    public List<Value> getOutput()
    {
        return this.output;
    }

    public void setOutput(List<Value> output)
    {
        this.output = output;
    }

    public IHeap<Value> getHeap()
    {
        return this.heap;
    }

    public void setHeap(IHeap<Value> heap)
    {
        this.heap = heap;
    }

    @Override
    public String toString()
    {
        return "ProgramState:{" + "ID: " + id + "ExecutionStack= " +
                executionStack.toString() + "\nSymbolTable= " +
                symbolTable.toString() + "\nOutput= " +
                output.toString() +"\nFiletable= " +
                FileTable.toString() + "\nHeap=" +
                heap.toString() + "\nLatchTable= " +
                latchTable.toString() + "}";
    }

    public int getID() {
        return id;
    }


}
