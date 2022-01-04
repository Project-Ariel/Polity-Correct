package com.example.polity_correct;

import com.google.firebase.Timestamp;

public class Citizen extends User {

    public Citizen(String userName, String password, String mail, Long yearOfBirth, long gender, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gender, userType, pg);
    }

    public void Vote(String proposition_key, double grade, StatusVote status, Timestamp date) {
        DB.updateVote(proposition_key, grade, status, date);
    }
}
