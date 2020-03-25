package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type{
    public boolean equals(Object second)
    {
        return second instanceof BoolType;
    }

    public String toString()
    {
        return "bool ";
    }

    public BoolValue defaultValue()
    {
        return new BoolValue(false);
    }
}
