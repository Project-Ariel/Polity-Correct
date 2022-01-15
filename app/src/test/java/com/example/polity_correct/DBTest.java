package com.example.polity_correct;

import static org.junit.Assert.*;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class DBTest {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

//    @Test
//    public void getPg() {
//    }

//    @Test
//    public void getPropositions() {
//    }

    @Test
    public void getPropVotes() {
    }

    @Test
    public void updateVote() {
        String proposition_key = "test";
        double grade = 5;
        StatusVote status = StatusVote.against;
        Timestamp current_date = new Timestamp(Calendar.getInstance().getTime());
        String user_pg = "user_pg";
        String[] realVote = {proposition_key, Double.toString(grade), status.toString(), current_date.toString(), user_pg};

        DB.updateVote(proposition_key, grade, status, current_date, user_pg);

        db.collection("Votes")
                .whereEqualTo("proposition_key", proposition_key)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String[] documentVote = {
                                    (String) document.get("proposition_key"),
                                    (String) document.get("vote_grade"),
                                    (String) document.get("user_choice"),
                                    (String) document.get("vote_date"),
                                    (String) document.get("user_key_pg")};

                            db.collection("Votes").document(document.getId()).delete();
                            assertArrayEquals("", realVote, documentVote);

                        }
                    }
                });
    }

    @Test
    public void getVotesSpecificPG() {
    }

    @Test
    public void getUser() {
    }

//    @Test
//    public void getNamePG() {
//    }

    @Test
    public void setCurrUser() {
    }
}