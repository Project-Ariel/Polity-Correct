package com.example.polity_correct;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Citizen extends User {

    public Citizen(String userName, String password, String mail, Long yearOfBirth, long gender, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth, gender, userType, pg);
    }

    public void Vote(Citizen citizen_details, String proposition_key, double grade, StatusVote status, Timestamp date) {
        FirebaseFirestore db;
        FirebaseAuth mAuth;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", mAuth.getCurrentUser().getUid());
        vote.put("user_name", citizen_details.getUserName());
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", status);
        vote.put("vote_grade", grade);
        vote.put("vote_date", date);

        db.collection("Votes").add(vote);
    }
}
