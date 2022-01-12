package com.example.polity_correct;

import com.google.firebase.firestore.Exclude;

public class User {
    private String userKey;
    private String userName;
    private String password;
    private String mail;
    private long yearOfBirth;
    private long gender;
    private UserType userType;
    private String key_pg;

    public User(String key) {
        this.userKey = key;
    }

    public User(String userName, String password, String mail, long yearOfBirth, long gender, UserType userType, String pg) {
        this.userName = userName;
        this.password = password;
        this.mail = mail;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.userType = userType;
        this.key_pg = pg;
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

    public long getYearOfBirth() {
        return yearOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setYearOfBirth(Long yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getKey_pg() {
        return key_pg;
    }

    public void setKey_pg(String key_pg) {
        this.key_pg = key_pg;
    }

    public long getGender() {
        return gender;
    }

    public void setGander(long gender) {
        this.gender = gender;
    }

    @Exclude
    public String getKey() {
        return userKey;
    }

    public void setKey(String userKey) {
        this.userKey = userKey;
    }


}

enum UserType {
    citizen,
    parliament,
}

enum StatusVote {
    against,
    abstain,
    agreement
}