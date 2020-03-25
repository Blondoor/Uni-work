package Model.ADTs;

import Model.Exceptions.MyException;

public interface List<T> {
    void add(T item);
    void remove(T item) throws MyException;
    T get(int i) throws MyException;
    int size();

    @Override
    String toString();

    java.util.List getContent();
}
