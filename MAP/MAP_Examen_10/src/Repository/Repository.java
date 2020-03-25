package Repository;

import Model.ADTs.MyList;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.PrgStmt.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> states;
    private int currentIndex;
    private String path;
    private boolean first;

    public Repository(String path)
    {
        this.states = new ArrayList<>();
        this.currentIndex = 0;
        this.path = path;
        this.first = false;
    }

    @Override
    public List<ProgramState> getStates()
    {
        return this.states;
    }

    @Override
    public void setStates(List<ProgramState> state)
    {
        states = state;
    }

    @Override
    public void addProgram(ProgramState state)
    {
        this.states.add(state);
    }

    /*@Override
    public ProgramState getCurrentProgram() throws MyException
    {
        return this.states.get(this.currentIndex);
    }*/

    @Override
    public void logProgramStateExecution(ProgramState state) throws IOException, MyException
    {
        PrintWriter printwriter;
        if(first)
        {
            printwriter = new PrintWriter(new BufferedWriter(new FileWriter(this.path,false)));
            this.first = true;
        }
        else
            printwriter = new PrintWriter(new BufferedWriter(new FileWriter(this.path,true)));
        printwriter.print(state.toString());
        printwriter.close();

    }

    @Override
    public ProgramState getProgramWithId(int id) {
        for (ProgramState p: states)
            if(p.getID()==id)
                return p;
        return null;
    }

}
