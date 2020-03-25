package View;

public abstract class Command {
    private String key, desc;

    public Command(String key, String desc)
    {
        this.key = key;
        this.desc = desc;
    }

    public abstract void execute();

    public String getKey()
    {
        return this.key;
    }

    public String getDesc()
    {
        return this.desc;
    }
}

