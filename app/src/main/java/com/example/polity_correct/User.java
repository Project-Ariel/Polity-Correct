package com.example.polity_correct;

public class User {
    private String userName;
    private String password;
    private String mail;
    private int yearOfBirth;
    private UserType userType;
    private int key_pg;

    public User(String userName, String password, String mail, int yearOfBirth, UserType userType, int pg){
        this.userName=userName;
        this.password=password;
        this.mail=mail;
        this.yearOfBirth=yearOfBirth;
        this.userType=userType;
        this.key_pg=pg;
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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getPg() {
        return key_pg;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPg(int pg) {
        this.key_pg = pg;
    }

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