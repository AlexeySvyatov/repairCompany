package com.example.repaircompany;

public class requestDB {
    public Integer idRequest;
    public String object;
    public String message;
    public String worker;
    public String date;

    public requestDB(Integer idRequest, String object, String message, String worker, String date){
        this.idRequest = idRequest;
        this.object = object;
        this.message = message;
        this.worker = worker;
        this.date = date;
    }

    public requestDB(){}

    public Integer getIdRequest(){return idRequest;}
    public void setIdRequest(Integer idRequest) {this.idRequest = idRequest;}

    public String getObject(){return object;}
    public void setObject(String object){this.object = object;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public String getWorker(){return worker;}
    public void setWorker(String worker){this.worker = worker;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
}
