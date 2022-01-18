package com.example.polity_correct;

import static org.junit.Assert.assertArrayEquals;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    //after connect to OData
//    @Test
//    public void getPg() {
//    }

    //after connect to OData
//    @Test
//    public void getPropositions() {
//    }

    //Test tat something back
//    @Test
//    public void getPropVotes() {
//    }

    @Test
    public void updateVote() {
        String proposition_key = "updateVote_test";
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
                            assertArrayEquals("updateVoteTest", realVote, documentVote);

                        }
                    }
                });
    }

//    @Test
//    public void getVotesSpecificPG() {
//    }

    //Test tat something back
    @Test
    public void getUser() {
        String userKey = "4cqfTFVuK0NUciqXyAND2DeUZjT2";
        String userName = "pol";
        String password = "112233";
        String mail = "pol@pol.pol";
        long yearOfBirth = 2021;
        long gender = 1;
        UserType userType = UserType.citizen;
        String key_pg = "JvPCOGnIXo3BuhwL1WFj";

        User user_test = new User(userKey);

        String[] realUser = {userName, password, mail, Long.toString(yearOfBirth), Long.toString(gender), String.valueOf(userType), key_pg};

        DB.getUser(user_test);

        String[] documentUser = {
                user_test.getUserName(),
                user_test.getPassword(),
                user_test.getMail(),
                Long.toString(user_test.getGender()),
                Long.toString(user_test.getYearOfBirth()),
                String.valueOf(user_test.getUserType()),
                user_test.getKey_pg()};


    }

    //after connect to OData
//    @Test
//    public void getNamePG() {
//    }

    @Test
    public void setCurrUser() {
        String userKey = "setCurrUser_test";
        String userName = "userName";
        String password = "password";
        String mail = "mail@gmail.com";
        long yearOfBirth = 2000;
        long gender = 0;
        UserType userType = UserType.citizen;
        String key_pg = "key_pg";

        User user_test = new User(userName, password, mail, yearOfBirth, gender, userType, key_pg);
        user_test.setKey(userKey);

        String[] realUser = {userName, password, mail, Long.toString(yearOfBirth), Long.toString(gender), String.valueOf(userType), key_pg};

        DB.setCurrUser(user_test);

        db.collection("Users")
                .whereEqualTo("userKey", userKey)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String[] documentUser = {
                                    (String) document.get("userName"),
                                    (String) document.get("password"),
                                    (String) document.get("mail"),
                                    (String) document.get("gender"),
                                    (String) document.get("userType"),
                                    (String) document.get("key_pg")};

                            db.collection("Users").document(userKey).delete();
                            assertArrayEquals("setCurrUserTest", realUser, documentUser);

                        }
                    }
                });
    }
}