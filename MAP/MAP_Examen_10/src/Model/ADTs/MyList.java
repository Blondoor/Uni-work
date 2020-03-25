package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;
import java.util.ArrayList;

public class MyList<T> implements Model.ADTs.List {
    private List<T> list;

    public MyList()
    {
        this.list = new ArrayList<T>();
    }

    @Override
    public void add(Object item)
    {
        this.list.add((T) item);
    }

    @Override
    public void remove(Object item) throws MyException
    {
        if(!this.list.contains((T) item))
            throw new MyException("Item doesn't exist");
        this.list.remove((T) item);
    }

    @Override
    public Object get(int index) throws MyException
    {
        if(index < 0 || index >= list.size())
            throw new MyException("Invalid index");
        return this.list.get(index);
    }

    @Override
    public int size()
    {
        return this.list.size();
    }

    @Override
    public String toString()
    {
        String s = "{";

        for(T el: this.list)
            s += el.toString() + " ";
        s += "}\n";
        return s.toString();
    }

    @Override
    public List getContent()
    {
        return list;
    }
}
