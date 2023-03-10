package com.example.repaircompany;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    @FXML private AnchorPane helloPane;
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
                if(!authLogin.getText().equals("") & !authPass.getText().equals("")){
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
                                setLabel(message, helloPane);
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    message.setText("Проверьте, все ли поля заполнены");
                    setLabel(message, helloPane);
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
                if(!authLogin.getText().equals("") & !authPass.getText().equals("")){
                    try{
                        Statement statement = DBHandler.getDbConnection().createStatement();
                        ResultSet resSet = statement.executeQuery("SELECT * FROM workers");
                        while(resSet.next()){
                            if(resSet.getString("login").equals(authLogin.getText().trim()) & resSet.getString("password").equals(authPass.getText().trim())){
                                idWorker = resSet.getInt("idWorker");
                                if(idWorker == 1){
                                    enterBtn.getScene().getWindow().hide();
                                    HelloApplication.openAnotherWindow("adminWindow.fxml");
                                }else{
                                    enterBtn.getScene().getWindow().hide();
                                    HelloApplication.openAnotherWindow("workerWindow.fxml");
                                }
                            }else{
                                message.setText("Проверьте корректность данных");
                                setLabel(message, helloPane);
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    message.setText("Проверьте, все ли поля заполнены");
                    setLabel(message, helloPane);
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
                if(!regLogin.getText().equals("") & !name.getText().equals("") & !phone.getText().equals("") & !birthDate.getValue().toString().equals("") & !regPass.getText().equals("") & !regPass2.getText().equals("")){
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher matcher = pattern.matcher(regPass.getText().trim());
                    if(!matcher.find() & regPass.getText().equals(regPass2.getText())) {
                        DBHandler.authorization(regLogin.getText().trim(), name.getText().trim(), phone.getText().trim(), birthDate, regPass.getText().trim());
                        DBHandler.newUser(name.getText().trim());
                        newIdUser = DBHandler.newIdUser;
                        enterBtn.getScene().getWindow().hide();
                        HelloApplication.openAnotherWindow("userWindow.fxml");
                    }else{
                        message.setText("Ошибка с паролем");
                        setLabel(message, helloPane);
                    }
                }else{
                    message.setText("Пожалуйста, заполните все поля");
                    setLabel(message, helloPane);
                }
            });
        });
    }

    private void setLabel(Label label, AnchorPane pane){
        label.setMaxWidth(Double.MAX_VALUE);
        pane.setLeftAnchor(label, 0.0);
        pane.setRightAnchor(label, 0.0);
        label.setAlignment(Pos.CENTER);
        message.setVisible(true);
    }
}