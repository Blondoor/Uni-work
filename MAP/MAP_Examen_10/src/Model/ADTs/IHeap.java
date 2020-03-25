package Model.ADTs;

import Model.Exceptions.MyException;
import Model.Expressions.Expression;

import java.util.Map;

public interface IHeap<T> {
    int allocate(Object val);
    T get(int addr);
    void put(int addr, Object val);
    T deallocate(int addr);
    void setContent(Map<Integer, T> content);
    Map<Integer, T> getContent();

    @Override
    String toString();

}
