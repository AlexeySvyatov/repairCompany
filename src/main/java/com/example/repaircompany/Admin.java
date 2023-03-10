package com.example.repaircompany;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

public class Admin {
    @FXML private Pane addPane;
    @FXML private Button addWorker;
    @FXML private Button deleteWorker;
    @FXML private Button deleteRequest;
    @FXML private Button backBtn;
    @FXML private Button addBtn;
    @FXML private TextField idField;
    @FXML private TextField loginField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passField;
    @FXML private DatePicker dateField;

    @FXML private TableView<workersDB> workersTable;
    @FXML private TableColumn<workersDB, Integer> idWorker;
    @FXML private TableColumn<workersDB, String> login;
    @FXML private TableColumn<workersDB, String> password;
    @FXML private TableColumn<workersDB, String> name;
    @FXML private TableColumn<workersDB, String> phone;
    @FXML private TableColumn<workersDB, Date> dateWorker;

    @FXML private TableView<requestADB> requestTable;
    @FXML private TableColumn<requestADB, Integer> idRequest;
    @FXML private TableColumn<requestADB, String> user;
    @FXML private TableColumn<requestADB, String> worker;
    @FXML private TableColumn<requestADB, String> object;
    @FXML private TableColumn<requestADB, String> message;
    @FXML private TableColumn<requestADB, Date> date;

    private ObservableList<workersDB> workers = FXCollections.observableArrayList();
    private ObservableList<requestADB> requests = FXCollections.observableArrayList();

    private workersDB selectedWorker = new workersDB();
    private requestADB selectedRequest = new requestADB();

    @FXML
    void initialize(){
        initWorkers();
        idWorker.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateWorker.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        workersTable.setItems(workers);

        initRequest();
        idRequest.setCellValueFactory(new PropertyValueFactory<>("idRequest"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestTable.setItems(requests);

        deleteWorker.setOnAction(deleteEvent -> {
            selectedWorker = workersTable.getSelectionModel().getSelectedItem();
            DBHandler.deleteRowWorker(selectedWorker.getIdWorker());
            workersTable.getItems().remove(selectedWorker);
        });

        deleteRequest.setOnAction(deleteEvent -> {
            selectedRequest = requestTable.getSelectionModel().getSelectedItem();
            DBHandler.deleteRowRequest(selectedRequest.getIdRequest());
            requestTable.getItems().remove(selectedRequest);
        });

        addWorker.setOnAction(actionEvent -> {
            addWorker.setVisible(false);
            deleteWorker.setVisible(false);
            deleteRequest.setVisible(false);
            workersTable.setVisible(false);
            requestTable.setVisible(false);
            addPane.setVisible(true);
            addBtn.setOnAction(addEvent -> {
                if(!idField.getText().equals("") & !loginField.getText().equals("") & !passField.getText().equals("") & !nameField.getText().equals("") & !phoneField.getText().equals("") & !dateField.getValue().toString().equals("")){
                    DBHandler.newWorker(Integer.parseInt(idField.getText().trim()), loginField.getText().trim(), passField.getText().trim(), nameField.getText().trim(), phoneField.getText().trim(), dateField);
                    addWorker.setVisible(true);
                    deleteWorker.setVisible(true);
                    deleteRequest.setVisible(true);
                    workersTable.setVisible(true);
                    requestTable.setVisible(true);
                    addPane.setVisible(false);
                }
            });
            backBtn.setOnAction(backEvent -> {
                addWorker.setVisible(true);
                deleteWorker.setVisible(true);
                deleteRequest.setVisible(true);
                workersTable.setVisible(true);
                requestTable.setVisible(true);
                addPane.setVisible(false);
            });
        });
    }

    private void initWorkers(){
        try{
            Connection dbConnection = DBHandler.getDbConnection();
            ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT idWorker, login, password, name, phone, birthDate FROM workers");
            while(resSet.next()){
                workers.add(new workersDB(resSet.getInt("idWorker"), resSet.getString("login"), resSet.getString("password"), resSet.getString("name"), resSet.getString("phone"), resSet.getDate("birthDate")));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void initRequest(){
        try{
            Connection dbConnection = DBHandler.getDbConnection();
            ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT request.idRequest, users.name, workers.name, request.object, request.message, request.date FROM request INNER JOIN workers ON workers.idWorker = request.idWorker INNER JOIN users ON users.idUser = request.idUser");
            while(resSet.next()){
                requests.add(new requestADB(resSet.getInt("idRequest"), resSet.getString("name"), resSet.getString("name"), resSet.getString("object"), resSet.getString("message"), resSet.getDate("date")));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}