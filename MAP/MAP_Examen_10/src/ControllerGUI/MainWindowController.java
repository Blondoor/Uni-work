package ControllerGUI;

import Controller.Controller;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.PrgStmt.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Value;
import com.sun.javafx.collections.MappingChange;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class MainWindowController implements Initializable {

    @FXML
    private ListView<String> exeStackVIew;
    @FXML
    private TableView<Map.Entry<String,Value>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symTableNames;
    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symTableValues;
    @FXML
    private Label progStatesCount;
    @FXML
    private Button execButton;
    @FXML
    private TableView<Map.Entry<Integer,Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapTableAddr;
    @FXML
    private TableColumn <Map.Entry<Integer, Value>, String> heapTableValues;
    @FXML
    private ListView<String> outputView;
    @FXML
    private ListView<String> fileTableView;
    @FXML
    private ListView<Integer> progIdentifiersView;
    private Controller controller;

    @FXML
    private TableView<Map.Entry<Integer,Integer>> latchTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,Integer>,String> latchTableLocations;
    @FXML
    private TableColumn<Map.Entry<Integer,Integer>,String> latchTableValues;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateProgStatesCount();
        populateIdentifiersView();
        execButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;
        //Heap
        heapTableAddr.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));
        //SymTable
        symTableNames.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        latchTableLocations.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        latchTableValues.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue() + " "));

        progIdentifiersView.setOnMouseClicked(mouseEvent -> changeProgramStateHandler(getSelectedProgramState()));

        //execButton.setDisable(true);
    }

    private void changeProgramStateHandler(ProgramState currentProgState){
        if(currentProgState==null)
            return;
        try {
            populateProgStatesCount();
            populateIdentifiersView();
            populateHeapTableView(currentProgState);
            populateOutputView(currentProgState);
            populateFileTableView(currentProgState);
            populateExeStackView(currentProgState);
            populateSymTableView(currentProgState);
            populateLatchTableView(currentProgState);

        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }

    }
    public void oneStepHandler(ActionEvent actionEvent) {
        if(controller==null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No program selected!");
            error.show();
            execButton.setDisable(true);
            return;
        }
        ProgramState programState = getSelectedProgramState();
        if(programState!=null && !programState.isNotCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR,"Nothing left to execute!");
            error.show();
            return;
        }
        try {
            controller.executeOneStep();
            changeProgramStateHandler(programState);
            if(controller.getRepo().getStates().size()==0)
                execButton.setDisable(true);
        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }

    }

    private void populateLatchTableView(ProgramState givenProgramState){
        latchTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getLatchTable().getLatchTable().entrySet())));
        latchTableView.refresh();
    }

    private void populateProgStatesCount(){
        progStatesCount.setText("No of Program States: " + controller.getRepo().getStates().size());
    }

    private void populateHeapTableView(ProgramState givenProgramState){
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getHeap().getContent().entrySet())));
        heapTableView.refresh();
    }

    private void populateOutputView(ProgramState givenProgramState) throws MyException {
        outputView.setItems(FXCollections.observableArrayList(givenProgramState.getOutput().getContent()));
    }

    private void populateFileTableView(ProgramState givenProgramState){
        fileTableView.setItems(FXCollections.observableArrayList(givenProgramState.getFileTable().getContent().keySet()));
    }
    private void populateIdentifiersView(){
        progIdentifiersView.setItems(FXCollections.observableArrayList(controller.getRepo().getStates().stream().map(ProgramState::getID).collect(Collectors.toList())));
        progIdentifiersView.refresh();
    }

    private void populateExeStackView(ProgramState givenProgramState){
        IStack<IStatement> stack = givenProgramState.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStatement stm : stack.getValues()){
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackVIew.setItems(FXCollections.observableArrayList(stackOutput));
    }
    private void populateSymTableView(ProgramState givenProgramState){
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSymbolTable().getContent().entrySet())));
        symbolTableView.refresh();
    }

    private ProgramState getSelectedProgramState(){
        if(progIdentifiersView.getSelectionModel().getSelectedIndex()==-1)
            return null;
        int id = progIdentifiersView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getProgramWithId(id);
    }
}