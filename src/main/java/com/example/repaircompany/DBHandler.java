package com.example.repaircompany;

import javafx.scene.control.DatePicker;

import java.sql.*;
import java.time.LocalDate;

public class DBHandler {
    static Connection dbConnection;
    public static int newIdUser = 0;

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localHost:3306/repaircompany";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "1234");
        return dbConnection;
    }

    public static void authorization(String login, String name, String phone, DatePicker birthDate, String password){
        Date dateBirth = Date.valueOf(birthDate.getValue());
        String query = "INSERT INTO users(login, password, name, phone, birthDate) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, name);
            prSt.setString(4, phone);
            prSt.setDate(5, dateBirth);
            prSt.executeUpdate();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static void addRequest(int idUser, int idWorker, String object, String message, LocalDate date){
        Date todayDate = Date.valueOf(date);
        String query = "INSERT INTO request(idUser, idWorker, object, message, date) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setInt(1, idUser);
            prSt.setInt(2, idWorker);
            prSt.setString(3, object);
            prSt.setString(4, message);
            prSt.setDate(5, todayDate);
            prSt.executeUpdate();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static int newUser(String name){
        try{
            Statement statement = DBHandler.getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
            while(resSet.next()){
                newIdUser = resSet.getInt("idUser");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return newIdUser;
    }

    public static void deleteRowRequest(int idRequest){
        String query = "DELETE FROM request WHERE idRequest = " + idRequest;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static void deleteRowWorker(int idWorker){
        String query = "DELETE FROM workers WHERE idWorker = " + idWorker;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.executeUpdate();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static void newWorker(int id, String login, String password, String name, String phone, DatePicker date){
        Date workerDate = Date.valueOf(date.getValue());
        String query = "INSERT INTO workers(idWorker, login, password, name, phone, birthDate) VALUES(?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setInt(1, id);
            prSt.setString(2, login);
            prSt.setString(3, password);
            prSt.setString(4, name);
            prSt.setString(5, phone);
            prSt.setDate(6, workerDate);
            prSt.executeUpdate();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}