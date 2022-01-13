package com.example.polity_correct;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ParliamentMember extends User {

    public ParliamentMember(String userName, String password, String mail, Long yearOfBirth, long gender, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gender, userType, pg);
    }

    public Task<QuerySnapshot> show_citizen_votes(Proposition curr_proposition, ArrayList<String> votes, ArrayList<Double> grades) {
        return DB.getPropVotes(votes, grades, curr_proposition.getKey());
    }

    public Task<QuerySnapshot> show_citizen_votes_specific_PG(Proposition curr_proposition, ArrayList<String> votes, ArrayList<Double> grades) {
        return DB.getVotesSpecificPG(votes, grades, curr_proposition);
    }
}