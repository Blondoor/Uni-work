package View;

import Controller.Controller;
import Model.Exceptions.MyException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller cntrl;

    public RunExample(String key, String desc, Controller cntrl)
    {
        super(key,desc);
        this.cntrl = cntrl;
    }

    @Override
    public void execute()
    {
        try
        {
            cntrl.executeAllSteps();
        }
        catch (MyException | IOException | InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
