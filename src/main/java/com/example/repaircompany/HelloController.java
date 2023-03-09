package com.example.repaircompany;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {
    @FXML private Label message;
    @FXML private TextField regLogin;
    @FXML private TextField name;
    @FXML private TextField phone;
    @FXML private TextField authLogin;
    @FXML private PasswordField regPass;
    @FXML private PasswordField regPass2;
    @FXML private PasswordField authPass;
    @FXML private DatePicker birthDate;
    @FXML private Button authBtn;
    @FXML private Button regBtn;
    @FXML private Button workBtn;
    @FXML private Button enterBtn;
    public static int idUser = 0;
    public static int idWorker = 0;
    public static int newIdUser = 0;

    @FXML
    void initialize(){
        authBtn.setOnAction(authEvent -> {
            authBtn.setVisible(false);
            regBtn.setVisible(false);
            workBtn.setVisible(false);
            enterBtn.setVisible(true);
            authLogin.setVisible(true);
            authPass.setVisible(true);
            enterBtn.setOnAction(enterEvent -> {
                if(!authLogin.equals("") & !authPass.equals("")){
                    try{
                        Statement statement = DBHandler.getDbConnection().createStatement();
                        ResultSet resSet = statement.executeQuery("SELECT * FROM users");
                        while(resSet.next()){
                            if(resSet.getString("login").equals(authLogin.getText().trim()) & resSet.getString("password").equals(authPass.getText().trim())){
                                idUser = resSet.getInt("idUser");
                                enterBtn.getScene().getWindow().hide();
                                HelloApplication.openAnotherWindow("userWindow.fxml");
                            }else{
                                message.setText("Проверьте корректность данных");
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    message.setText("Проверьте, все ли поля заполнены");
                }
            });
        });

        workBtn.setOnAction(authEvent -> {
            workBtn.setVisible(false);
            regBtn.setVisible(false);
            authBtn.setVisible(false);
            enterBtn.setVisible(true);
            authLogin.setVisible(true);
            authPass.setVisible(true);
            enterBtn.setOnAction(enterEvent -> {
                if(!authLogin.equals("") & !authPass.equals("")){
                    try{
                        Statement statement = DBHandler.getDbConnection().createStatement();
                        ResultSet resSet = statement.executeQuery("SELECT * FROM workers");
                        while(resSet.next()){
                            if(resSet.getString("login").equals(authLogin.getText().trim()) & resSet.getString("password").equals(authPass.getText().trim())){
                                idWorker = resSet.getInt("idWorker");
                                enterBtn.getScene().getWindow().hide();
                                HelloApplication.openAnotherWindow("workerWindow.fxml");
                            }else{
                                message.setText("Проверьте корректность данных");
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    message.setText("Проверьте, все ли поля заполнены");
                }
            });
        });

        regBtn.setOnAction(regEvent -> {
            regBtn.setVisible(false);
            authBtn.setVisible(false);
            workBtn.setVisible(false);
            enterBtn.setVisible(true);
            regLogin.setVisible(true);
            name.setVisible(true);
            phone.setVisible(true);
            birthDate.setVisible(true);
            regPass.setVisible(true);
            regPass2.setVisible(true);
            enterBtn.setText("Регистрация");
            enterBtn.setOnAction(enterEvent -> {
                if(!regLogin.equals("") & !name.equals("") & !phone.equals("") & !birthDate.equals("") & !regPass.equals("") & !regPass2.equals("")){
                    DBHandler.authorization(regLogin.getText().trim(), name.getText().trim(), phone.getText().trim(), birthDate, regPass.getText().trim());
                    DBHandler.newUser(name.getText().trim());
                    newIdUser = DBHandler.newIdUser;
                    enterBtn.getScene().getWindow().hide();
                    HelloApplication.openAnotherWindow("userWindow.fxml");
                }else{
                    message.setText("Пожалуйста, заполните все поля");
                }
            });
        });
    }
}