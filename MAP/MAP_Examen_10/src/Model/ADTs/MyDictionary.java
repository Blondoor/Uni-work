package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary {
    private HashMap<K, V> dict;

    public MyDictionary()
    {
        dict = new HashMap<K, V>();
    }

    @Override
    public Object lookup(Object key) throws MyException {
        if(!dict.containsKey((K) key))
            throw new MyException("Key doesn't exist");
        return dict.get((K) key);
    }

    @Override
    public String toString()
    {
        String s = "{";
        for(K key: dict.keySet())
            s += key.toString() + "->" + dict.get(key).toString() + ";";
        s += "}";
        return s;
    }

    @Override
    public boolean isDefined(Object key)
    {
        return dict.containsKey((K) key);
    }

    @Override
    public void remove(Object key) throws MyException
    {
        if(!dict.containsKey(key))
            throw new MyException("Key doesn't exist");
        dict.remove(key);
    }

    @Override
    public IDictionary clone()
    {
        IDictionary<K, V> newdict = new MyDictionary<>();
        for(K k:dict.keySet())
        {
            newdict.update(k, dict.get(k));
        }
        return newdict;
    }

    @Override
    public void update(Object key, Object value)
    {
        dict.put((K) key, (V) value);
    }

    @Override
    public Map getContent()
    {
        return dict;
    }
}
