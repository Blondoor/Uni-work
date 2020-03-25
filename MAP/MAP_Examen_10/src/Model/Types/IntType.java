package Model.Types;

import Model.Values.IntValue;

public class IntType implements Type{
    public boolean equals(Object second)
    {
        return second instanceof IntType;
    }

    public String toString()
    {
        return "int ";
    }
    public IntValue defaultValue()
    {
        return new IntValue(0);
    }
}
