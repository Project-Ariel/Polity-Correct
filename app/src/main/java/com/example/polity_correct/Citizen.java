package com.example.polity_correct;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class Citizen extends User{

    private String id;

    public Citizen(String id, String userName, String password, String mail, Long yearOfBirth, int gender, UserType userType, String pg) {
        super(userName, password, mail, yearOfBirth,gender, userType, pg);
        this.id=id;

    }
    public  Citizen(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void Vote(Citizen citizen_details,String proposition_key, double grade, StatusVote status, Timestamp date){
        FirebaseFirestore db;
        FirebaseAuth mAuth;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", mAuth.getCurrentUser().getUid());
        vote.put("user_name",citizen_details.getUserName());
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", status);
        vote.put("vote_grade", grade);
        vote.put("vote_date", date);

        db.collection("Votes")
                .add(vote)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    private static final String TAG = " ";

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    private final Object TAG = " ";

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w((String) TAG, "Error adding document", e);
                    }
                });
    }
}
