package com.example.repaircompany;

import java.sql.Date;

public class requestADB {
    public Integer idRequest;
    public String object;
    public String message;
    public String user;
    public String worker;
    public Date date;

    public requestADB(Integer idRequest, String user, String worker, String object, String message, Date date){
        this.idRequest = idRequest;
        this.user = user;
        this.worker = worker;
        this.object = object;
        this.message = message;
        this.date = date;
    }

    public requestADB(){}

    public Integer getIdRequest(){return idRequest;}
    public void setIdRequest(Integer idRequest) {this.idRequest = idRequest;}

    public String getUser(){return user;}
    public void setUser(String user){this.user = user;}

    public String getWorker(){return worker;}
    public void setWorker(String worker){this.worker = worker;}

    public String getObject(){return object;}
    public void setObject(String object){this.object = object;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public Date getDate(){return date;}
    public void setDate(Date date){this.date = date;}
}