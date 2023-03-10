package com.example.repaircompany;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class User {
    @FXML private AnchorPane userPane;
    @FXML private Label welcome;
    @FXML private Button newRequest;
    @FXML private Button myRequest;
    @FXML private Button deleteRequest;
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
        try {
            ResultSet resSet;
            if(HelloController.idUser == 0){
                resSet = DBHandler.getDbConnection().createStatement().executeQuery("SELECT * FROM users WHERE idUser = " + HelloController.newIdUser);
            }else{
                resSet = DBHandler.getDbConnection().createStatement().executeQuery("SELECT * FROM users WHERE idUser = " + HelloController.idUser);
            }
            while(resSet.next()){
                welcome.setText("Добро пожаловать, \n" + resSet.getString("name"));
                setLabel(welcome, userPane);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        initWorker();
        initRequest();
        idRequest.setCellValueFactory(new PropertyValueFactory<>("idRequest"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestTable.setItems(requests);
        newRequest.setOnAction(actionEvent -> {
            newRequest.setVisible(false);
            myRequest.setVisible(false);
            requestTable.setVisible(false);
            deleteRequest.setVisible(false);
            requestPane.setVisible(true);
            createRequest.setOnAction(addEvent -> {
                if(!objectField.getText().equals("") & ! messageField.getText().equals("") & !workerCB.getValue().equals("")){
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
                newRequest.setVisible(true);
                myRequest.setVisible(true);
                notice.setVisible(false);
                objectField.clear();
                messageField.clear();
                workerCB.getSelectionModel().clearSelection();
            });
        });

        myRequest.setOnAction(actionEvent -> {
            requestTable.setVisible(true);
            deleteRequest.setVisible(true);
            deleteRequest.setOnAction(deleteEvent -> {
                selectedRequest = requestTable.getSelectionModel().getSelectedItem();
                DBHandler.deleteRowRequest(selectedRequest.getIdRequest());
                requestTable.getItems().remove(selectedRequest);
            });
        });
    }

    private void initRequest(){
        try{
            Connection dbConnection = DBHandler.getDbConnection();
            if(HelloController.idUser == 0){
                ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT request.idRequest, request.object, request.message, workers.name, request.date FROM repaircompany.request inner join repaircompany.workers on request.idWorker = workers.idWorker where idUser = " + HelloController.newIdUser);
                while(resSet.next()){
                    requests.add(new requestDB(resSet.getInt("idRequest"), resSet.getString("object"), resSet.getString("message"), resSet.getString("name"), resSet.getString("date")));
                }
            }else{
                ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT request.idRequest, request.object, request.message, workers.name, request.date FROM repaircompany.request inner join repaircompany.workers on request.idWorker = workers.idWorker where idUser = " + HelloController.idUser);
                while(resSet.next()){
                    requests.add(new requestDB(resSet.getInt("idRequest"), resSet.getString("object"), resSet.getString("message"), resSet.getString("workers.name"), resSet.getString("date")));
                }
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

    private void setLabel(Label label, AnchorPane pane){
        label.setMaxWidth(Double.MAX_VALUE);
        pane.setLeftAnchor(label, 0.0);
        pane.setRightAnchor(label, 0.0);
        label.setAlignment(Pos.CENTER);
    }
}