package com.example.repaircompany;

public class requestWDB {
    public Integer idRequest;
    public String object;
    public String message;
    public String user;
    public String date;

    public requestWDB(Integer idRequest, String object, String message, String user, String date){
        this.idRequest = idRequest;
        this.object = object;
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public requestWDB(){}

    public Integer getIdRequest(){return idRequest;}
    public void setIdRequest(Integer idRequest) {this.idRequest = idRequest;}

    public String getObject(){return object;}
    public void setObject(String object){this.object = object;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public String getUser(){return user;}
    public void setUser(String user){this.user = user;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
}