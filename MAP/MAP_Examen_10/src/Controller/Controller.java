package Controller;

import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Statements.IStatement;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.sql.Ref;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private String ProgramState;
    private ExecutorService executor;

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }

    public IRepository getRepo()
    {
        return this.repo;
    }

    public void addProgram(ProgramState prg)
    {
        this.repo.addProgram(prg);
    }

    /*public ProgramState executeOneStep(ProgramState state) throws MyException, IOException
    {
        IStack<IStatement> stack = state.getExecutionStack();

        if(stack.isEmpty())
            throw new MyException("The execution stack is empty");

        IStatement currentStmt = stack.pop();
        return currentStmt.execute(state);
    }*/

    public void executeOneStep() throws MyException
    {
        executor = Executors.newFixedThreadPool(2);
        repo.setStates(removeCompletedPrograms(repo.getStates()));
        List<ProgramState> programStates = repo.getStates();

        if(programStates.size() > 0)
        {
            try {
                oneStepForAllPrograms(repo.getStates());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repo.setStates(removeCompletedPrograms(repo.getStates()));
            executor.shutdownNow();
            callGarbageCollector(programStates);
        }
    }

    /*public String executeOneStepW() throws MyException
    {
        ProgramState prg = repo.getCurrentProgram();
        if(!prg.getExecutionStack().isEmpty())
        {

            return executeOneStep(prg).toString();
        }
        throw new MyException("the execution stack is empty");
    }*/

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList)
    {
        return inProgramList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesFromSymbolTable, Map<Integer,Value> heap)
    {
        return heap.entrySet()
                .stream()
                .filter(e->addressesFromSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void callGarbageCollector(List<ProgramState> states)
    {
        states.forEach(
                p-> {p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymbolTable().getContent().values(),p.getHeap().getContent().values()),p.getHeap().getContent()));}
        );
    }

    public String getPrgState()
    {
        return this.ProgramState;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setController(Controller cntrl)
    {
        this.repo = cntrl.getRepo();
        this.executor = cntrl.getExecutor();
        this.ProgramState = cntrl.getPrgState();
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableVals, Collection<Value> heap)
    {
        return  Stream.concat(
                heap.stream()
                        .filter(v-> v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}),
                symTableVals.stream()
                        .filter(v-> v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
        )
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programStateList) throws InterruptedException {
        programStateList.forEach(program -> {
            try {
                repo.logProgramStateExecution(program);
            } catch (IOException | MyException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callableList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(()-> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(p -> p != null)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramList);

        programStateList.forEach(program -> {
            try {
                repo.logProgramStateExecution(program);
            } catch (IOException | MyException e) {
                e.printStackTrace();
            }
        });

        repo.setStates(programStateList);
    }

    public void executeAllSteps() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        //remove the completed programs
        List<ProgramState> programStates = removeCompletedPrograms(repo.getStates());

        while(programStates.size() > 0)
        {
            callGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrograms(repo.getStates());
        }

        executor.shutdownNow();
        //programStates = removeCompletedPrograms(repo.getStates());
        repo.setStates(programStates);
    }
}
