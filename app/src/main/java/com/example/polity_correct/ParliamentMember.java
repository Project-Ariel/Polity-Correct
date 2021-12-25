package com.example.polity_correct;

import java.util.Date;

public class ParliamentMember extends User{

    public ParliamentMember(String userName, String password, String mail, int yearOfBirth, int gander, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gander, userType, pg);
    }

    //copy constructor
    public ParliamentMember(User user){
        super(user.getUserName(), user.getPassword(), user.getMail(), user.getYearOfBirth(), user.getGander(), user.getUserType(), user.getKey_pg());
    }


    public void show_citizen_votes(Proposition curr_prop){

    }
}
