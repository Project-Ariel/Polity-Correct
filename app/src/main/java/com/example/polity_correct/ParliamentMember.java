package com.example.polity_correct;

import java.util.Date;

public class ParliamentMember extends User{

    public ParliamentMember(String userName, String password, String mail, int yearOfBirth, UserType userType, int pg) {
        super(userName, password, mail, yearOfBirth, userType, pg);
    }

    public void show_citizen_votes(Proposition curr_prop){

    }
}
