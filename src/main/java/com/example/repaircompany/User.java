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
import java.sql.Statement;
import java.time.LocalDate;

public class User {
    @FXML private Label welcome;
    @FXML private Button newRequest;
    @FXML private Button myRequest;
    @FXML private Pane requestPane;
    @FXML private Button createRequest;
    @FXML private Button backBtn;
    @FXML private ComboBox<String> workerCB;
    @FXML private TextField objectField;
    @FXML private TextArea messageField;
    @FXML private Label notice;

    @FXML private TableView<requestDB> requestTable;
    @FXML private TableColumn<requestDB, Integer> idRequest;
    @FXML private TableColumn<requestDB, String> object;
    @FXML private TableColumn<requestDB, String> message;
    @FXML private TableColumn<requestDB, String> worker;
    @FXML private TableColumn<requestDB, Date> date;

    public static int idWorker = 0;

    private ObservableList<String> workers = FXCollections.observableArrayList();
    private ObservableList<requestDB> requests = FXCollections.observableArrayList();

    private requestDB selectedRequest = new requestDB();

    @FXML
    void initialize(){
        initWorker();
        initRequest();
        idRequest.setCellValueFactory(new PropertyValueFactory<>("idRequest"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        newRequest.setOnAction(actionEvent -> {
            requestPane.setVisible(true);
            createRequest.setOnAction(addEvent -> {
                if(!objectField.equals("") & ! messageField.equals("") & !workerCB.getValue().equals("")){
                    try{
                        Statement statement = DBHandler.getDbConnection().createStatement();
                        ResultSet resSet = statement.executeQuery("SELECT * FROM workers WHERE name = '" + workerCB.getValue() + "'");
                        while(resSet.next()){
                            idWorker = resSet.getInt("idWorker");
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    LocalDate localDate = LocalDate.now();
                    if(HelloController.idUser == 0){
                        DBHandler.addRequest(HelloController.newIdUser, idWorker, objectField.getText().trim(), messageField.getText().trim(), localDate);
                    }else{
                        DBHandler.addRequest(HelloController.idUser, idWorker, objectField.getText().trim(), messageField.getText().trim(), localDate);
                    }
                    requestPane.setVisible(false);
                }else{
                    notice.setText("Пожалуйста, заполните все поля и выберите работника");
                }
            });
            backBtn.setOnAction(backEvent -> {
                requestPane.setVisible(false);
            });
        });
    }

    private void initRequest(){
        try{
            Connection dbConnection = DBHandler.getDbConnection();
            ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT idRequest, object, message, (SELECT name FROM workers where idWorker = 1), date FROM request");
            while(resSet.next()){
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void initWorker(){
        try {
            Statement statement = DBHandler.getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery("SELECT name FROM workers");
            while (resSet.next()) {
                workers.add(resSet.getString("name"));
            }
            workerCB.getItems().addAll(workers);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
