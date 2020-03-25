package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public String toString()
    {
        return this.locationType.toString();
    }

    public int getAddress()
    {
        return this.address;
    }

    public Type getLocationType()
    {
        return this.locationType;
    }

    public void setAddress(int address)
    {
        this.address = address;
    }

    public Type getType()
    {
        return new RefType(locationType);
    }

    public void setLocationType(Type locationType)
    {
        this.locationType = locationType;
    }
}
