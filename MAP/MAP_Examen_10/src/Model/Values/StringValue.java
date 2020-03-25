package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String string;

    public StringValue(String string)
    {
        this.string = string;
    }

    public String getValue()
    {
        return this.string;
    }

    public String toString()
    {
        return this.string;
    }

    public Type getType()
    {
        return new StringType();
    }

}
