package com.example.polity_correct;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class Citizen extends User{

    private String id;

    public Citizen(String id, String userName, String password, String mail, Date yearOfBirth, UserType userType, PoliticalGroup pg) {
        super(userName, password, mail, yearOfBirth, userType, pg);
        this.id=id;

    }

    public boolean Vote(int proposition_key, int grade, StatusVote status){
        //update DB
        return true;
    }
}
