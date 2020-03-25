package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws MyException
    {
        return stack.pop();
    }
    @Override
    public String toString()
    {
        String s = "{";
        for(T el:this.stack)
            s += el.toString() + "|";
        s += "}";
        return s.toString();
    }
    @Override
    public void push(Object value)
    {
        stack.push((T) value);
    }
    @Override
    public boolean isEmpty()
    {
        return stack.empty();
    }

    @Override
    public List getValues()
    {
        return stack.subList(0, stack.size());
    }
}
