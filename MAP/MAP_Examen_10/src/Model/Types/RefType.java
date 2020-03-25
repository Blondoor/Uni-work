package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {
    private Type inner;

    public RefType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return this.inner;
    }

    public boolean equals(Object second)
    {
        if(second instanceof RefType)
            return inner.equals(((RefType) second).getInner());
        else return false;
    }

    @Override
    public String toString()
    {
        return "Ref(" + this.inner.toString() + ")";
    }

    public Value defaultValue()
    {
        return new RefValue(0, inner);
    }
}
