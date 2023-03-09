package com.example.repaircompany;

public class requestDB {
    public Integer idRequest;
    public Integer idUser;
    public Integer idWorker;
    public String object;
    public String message;
    public String date;

    public requestDB(Integer idRequest, Integer idUser, Integer idWorker, String object, String message, String date){
        this.idRequest = idRequest;
        this.idUser = idUser;
        this.idWorker = idWorker;
        this.object = object;
        this.message = message;
        this.date = date;
    }

    public requestDB(){}

    public Integer getIdRequest(){return idRequest;}
    public void setIdRequest(Integer idRequest) {this.idRequest = idRequest;}

    public Integer getIdUser(){return idUser;}
    public void setIdUser(Integer idUser) {this.idUser = idUser;}

    public Integer getIdWorker(){return idWorker;}
    public void setIdWorker(Integer idWorker) {this.idWorker = idWorker;}

    public String getObject(){return object;}
    public void setObject(String object){this.object = object;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
}
