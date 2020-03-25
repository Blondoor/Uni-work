package Model.ADTs;

import Model.Exceptions.MyException;
import Model.Statements.IStatement;

import java.util.List;

public interface IStack<T> {
    T pop() throws MyException;
    void push(T val);
    boolean isEmpty();

    @Override
    String toString();

    List<T> getValues();
}
