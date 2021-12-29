package com.example.polity_correct;

import java.util.Date;

public class ParliamentMember extends User{

    public ParliamentMember(String userName, String password, String mail, Long yearOfBirth, long gander, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gander, userType, pg);
    }


    public void show_citizen_votes(Proposition curr_prop){

    }
}
