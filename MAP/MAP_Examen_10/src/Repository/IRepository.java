package Repository;

import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface IRepository {
    public void addProgram(ProgramState program);
    List<ProgramState> getStates();
    void setStates(List<ProgramState> states);
    //public ProgramState getCurrentProgram() throws MyException;
    void logProgramStateExecution(ProgramState state) throws IOException, MyException;


    ProgramState getProgramWithId(int id);
}
