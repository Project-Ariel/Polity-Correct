package com.example.polity_correct;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class ParliamentMember extends User {

    public ParliamentMember(String userName, String password, String mail, Long yearOfBirth, long gander, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gander, userType, pg);
    }

    public Task<QuerySnapshot> show_citizen_votes(Proposition curr_proposition, double[] res) {
        return DB.getPropVotes(res, curr_proposition.getKey());
    }

    public void show_citizen_votes(Proposition curr_proposition, PoliticalGroup pg) {

    }
}