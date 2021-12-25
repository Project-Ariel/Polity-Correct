package com.example.polity_correct;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class Citizen extends User{

    private String id;

    public Citizen(String id, String userName, String password, String mail, int yearOfBirth,int gender, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth,gender, userType, pg);
        this.id=id;

    }
    public Citizen(User user){
        super(user.getUserName(), user.getPassword(), user.getMail(), user.getYearOfBirth(), user.getGander(), user.getUserType(), user.getKey_pg());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void Vote(int proposition_key, int grade, StatusVote status, Timestamp date){
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", id);
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", status);
        vote.put("vote_grade", grade);
        vote.put("vote_date", date);
    }
}
