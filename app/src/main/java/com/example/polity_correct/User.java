package com.example.polity_correct;

import java.util.Date;

public class User {
//    private String id;
    private String userName;
    private String password;
    private String mail;
    private Date yearOfBirth;
    private UserType userType;
    private PoliticalGroup pg;

    public User(String userName, String password, String mail, Date yearOfBirth, UserType userType, PoliticalGroup pg){
        this.userName=userName;
        this.password=password;
        this.mail=mail;
        this.yearOfBirth=yearOfBirth;
        this.userType=userType;
        this.pg=pg;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }

    public PoliticalGroup getPg() {
        return pg;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPg(PoliticalGroup pg) {
        this.pg = pg;
    }

    public boolean Vote(int proposition_key, int grade, StatusVote status){
        //update DB
        return true;
    }
}

enum PoliticalGroup {
    Yemina,
    CaholLavan,
    Merech
}

enum UserType{
    citizen,
    parliament
}

enum StatusVote{
    against,
    impossible,
    agreement
}