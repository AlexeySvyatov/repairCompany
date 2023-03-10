package com.example.repaircompany;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

public class Worker {
    @FXML private AnchorPane workerPane;
    @FXML private Label welcome;

    @FXML private TableView<requestWDB> requestTable;
    @FXML private TableColumn<requestWDB, Integer> idRequest;
    @FXML private TableColumn<requestWDB, String> object;
    @FXML private TableColumn<requestWDB, String> message;
    @FXML private TableColumn<requestWDB, String> user;
    @FXML private TableColumn<requestWDB, Date> date;

    private ObservableList<requestWDB> requests = FXCollections.observableArrayList();

    @FXML
    void initialize(){
        try {
            ResultSet resSet;
            resSet = DBHandler.getDbConnection().createStatement().executeQuery("SELECT * FROM workers WHERE idWorker = " + HelloController.idWorker);
            while(resSet.next()){
                welcome.setText("Добро пожаловать, \n" + resSet.getString("name"));
                setLabel(welcome, workerPane);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        initRequest();
        idRequest.setCellValueFactory(new PropertyValueFactory<>("idRequest"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestTable.setItems(requests);
    }

    private void initRequest(){
        try{
            Connection dbConnection = DBHandler.getDbConnection();
            ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT request.idRequest, request.object, request.message, users.name, request.date FROM repaircompany.request inner join repaircompany.users on request.idUser = users.idUser");
            while(resSet.next()){
                requests.add(new requestWDB(resSet.getInt("idRequest"), resSet.getString("object"), resSet.getString("message"), resSet.getString("name"), resSet.getString("date")));
            }
        }catch(Exception ex){
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