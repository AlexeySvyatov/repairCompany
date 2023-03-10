package com.example.repaircompany;

import java.sql.Date;

public class workersDB {
    public Integer idWorker;
    public String login;
    public String password;
    public String name;
    public String phone;
    public Date birthDate;

    public workersDB(Integer idWorker, String login, String password, String name, String phone, Date birthDate){
        this.idWorker = idWorker;
        this.login = login;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public workersDB(){}

    public Integer getIdWorker(){return idWorker;}
    public void setIdWorker(Integer idWorker) {this.idWorker = idWorker;}

    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}

    public Date getBirthDate(){return birthDate;}
    public void setBirthDate(Date birthDate){this.birthDate = birthDate;}
}