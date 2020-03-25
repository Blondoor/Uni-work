package Model.Types;

import Model.Values.StringValue;

public class StringType implements Type {
    public boolean equals(Object second)
    {
        return second instanceof StringType;
    }

    public String toString()
    {
        return "string ";
    }

    public StringValue defaultValue()
    {
        return new StringValue("");
    }
}
