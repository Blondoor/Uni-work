package ControllerGUI;
import Controller.Controller;
import ControllerGUI.MainWindowController;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.PrgStmt.ProgramState;
import Model.Statements.*;
import Model.Statements.LatchTable.CountDownStatement;
import Model.Statements.LatchTable.NewLatch;
import Model.Statements.LatchTable.awaitStatement;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {
    @FXML
    private Button selectBttn;
    @FXML
    private ListView<IStatement> selectItemListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent) {
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStatement> initExamples() {
        List<IStatement> list = new ArrayList<>();
        /*IStatement ex1 = new CompoundStatement(
            new VarDeclarationStatement("a", new RefType(new IntType())),new CompoundStatement(
                    new VarDeclarationStatement("b", new RefType(new IntType())), new CompoundStatement(
                            new VarDeclarationStatement("v", new IntType()), new CompoundStatement(
                                new HeapAllocation("a", new ValueExpression(new IntValue(0))), new CompoundStatement(
                                        new HeapAllocation("b", new ValueExpression(new IntValue(0))), new CompoundStatement(
                                                new HeapWriting("a", new ValueExpression(new IntValue(1))), new CompoundStatement(
                                                        new HeapWriting("b", new ValueExpression(new IntValue(2))), new CompoundStatement(
                                                                new ConditionalAssignmentStatement("v", new RelationalExpression(
                                                                        new HeapReading(new VariableExpression("a")), new HeapReading(new VariableExpression("b")), "<"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))), new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")), new CompoundStatement(
                                                                                new ConditionalAssignmentStatement("v", new RelationalExpression(
                                                                                        new HeapReading(new VariableExpression("a")), new ArithmeticExpression('-', new HeapReading(new VariableExpression("b")), new ValueExpression(new IntValue(2))), "<"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))), new PrintStatement(new VariableExpression("v")))
                                                                        ))
                                                                ))
                                                        ))
                                                )));*/

        /*IStatement ex1 = new CompoundStatement(
                new VarDeclarationStatement("v1", new RefType(new IntType())), new CompoundStatement(
                        new VarDeclarationStatement("v2", new RefType(new IntType())), new CompoundStatement(
                                new VarDeclarationStatement("v3", new RefType(new IntType())), new CompoundStatement(
                                        new VarDeclarationStatement("cnt", new IntType()), new CompoundStatement(
                                                new HeapAllocation("v1", new ValueExpression(new IntValue(2))), new CompoundStatement(
                                                        new HeapAllocation("v2", new ValueExpression(new IntValue(3))), new CompoundStatement(
                                                                new HeapAllocation("v3", new ValueExpression(new IntValue(4))), new CompoundStatement(
                                                                        new NewLatch("cnt", new HeapReading(new VariableExpression("v2"))), new CompoundStatement(
                                                                                new forkStatement(new HeapWriting("v1", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10))))), new CompoundStatement(
                                                                                        new forkStatement(new CompoundStatement(
                                                                                                new HeapWriting("v2", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))), new CompoundStatement(
                                                                                                        new PrintStatement(new HeapReading(new VariableExpression("v2"))), new CompoundStatement(
                                                                                                                new CountDownStatement("cnt"), new CompoundStatement(new forkStatement(new HeapWriting("v3", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v3")), new ValueExpression(new IntValue(10))))), new CompoundStatement(
                                                                                                                        new PrintStatement(new HeapReading(new VariableExpression("v3"))), new CompoundStatement(
                                                                                                                                new CountDownStatement("cnt"), new awaitStatement("cnt"))))))))
        , new CompoundStatement(
                        new PrintStatement(new ValueExpression(new IntValue(100))), new CompoundStatement(
                                new CountDownStatement("cnt"), new PrintStatement(new ValueExpression(new IntValue(100)))
        ))))))))))));*/
        IStatement ex1 = new CompoundStatement(new VarDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VarDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VarDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(new VarDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new HeapAllocation("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new HeapAllocation("v2",new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new HeapAllocation("v3",new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewLatch("cnt", new HeapReading(new VariableExpression("v2"))),
                                                                        new CompoundStatement(new forkStatement(new CompoundStatement(new HeapWriting("v1", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v1"))),
                                                                                        new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                new forkStatement(new CompoundStatement(new HeapWriting("v2", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                        new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v2"))),
                                                                                                                    new CompoundStatement(new CountDownStatement("cnt"), new forkStatement(new CompoundStatement(new HeapWriting("v3", new ArithmeticExpression('*', new HeapReading(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                                            new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v3"))),new CountDownStatement("cnt")))))))))))),
                                                                                new CompoundStatement(new awaitStatement("cnt"),
                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))))))
                                                                ))))))));
        list.add(ex1);
        return list;
    }

    private void displayExamples() {
        List<IStatement> examples = initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        selectBttn.setOnAction(actionEvent -> {
            try {
                int index = selectItemListView.getSelectionModel().getSelectedIndex();
                IStatement selectedProgram = selectItemListView.getItems().get(index);
                index++;
                ProgramState programState = new ProgramState(selectedProgram);
                IRepository repository = new Repository("log" + index + ".txt");
                Controller controller = new Controller(repository);
                controller.addProgram(programState);
                try {
                    selectedProgram.typecheck(new MyDictionary<String, Type>());
                    mainWindowController.setController(controller);
                } catch (MyException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.show();
                }
                // selectItemListView.getSelectionModel().select(null);
            } catch (IndexOutOfBoundsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "First you have to select the program");
                alert.show();
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayExamples();
    }
}