package com.example.polity_correct;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //get Political Groups from DB
    public static Task<QuerySnapshot> getPg(ArrayList<PoliticalGroup> pg) {
        return db.collection("PoliticalGroups")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PoliticalGroup p = new PoliticalGroup(document.getId(), (String) document.get("group_name"), (String) document.get("abbreviation"), (String) document.get("group_website"));
                            pg.add(p);
                        }
                    }
                });
    }

    public static Task<QuerySnapshot> getPropositions(ArrayList<Proposition> propositions, boolean voted) {
        return db.collection("Propositions")
                .whereEqualTo("voted", voted)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), voted);
                            propositions.add(p);
                        }
                        Collections.sort(propositions);
                    }
                });
    }

    public static Task<QuerySnapshot> getPropVotes(ArrayList<String> votes, ArrayList<Double> grades, String prop_key) {
        return db.collection("Votes")
                .whereEqualTo("proposition_key", prop_key)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            votes.add((String) document.get("user_choice"));
                            grades.add(Double.parseDouble("" + document.get("vote_grade").toString()));
                        }
                    }
                });
    }

    public static void updateVote(String proposition_key, double grade, StatusVote status, Timestamp date) {
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", mAuth.getCurrentUser().getUid());
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", status);
        vote.put("vote_grade", grade);
        vote.put("vote_date", date);

        db.collection("Votes").add(vote);
    }

    private static String voteKeyPG;

    public static Task<QuerySnapshot> getVotesSpecificPG(ArrayList<String> votes, ArrayList<Double> grades, Proposition curr_proposition) {
        return FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", curr_proposition.getKey())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String voteUserId = (String) document.get("user_id");
                            //get key_pg of user vote
                            getKeyPoliticalGroup(voteUserId).addOnCompleteListener(task1 -> {
                                if (voteKeyPG.equals(Login.getCurrUser().getKey_pg())) {
                                    votes.add((String) document.get("user_choice"));
                                    grades.add(Double.parseDouble("" + document.get("vote_grade").toString()));
                                }
                            });
                        }
                    }
                });
    }

    private static Task<DocumentSnapshot> getKeyPoliticalGroup(String id_user) {
        return FirebaseFirestore.getInstance().collection("Users")
                .document(id_user)
                .get().addOnCompleteListener(task -> {
                    voteKeyPG = task.getResult().get("key_pg").toString();
                });
    }

    //send to function new user init user=new User()
    public static Task<DocumentSnapshot> getUser(User user) {
        return db.collection("Users").document(user.getKey()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setUserName((String) task.getResult().get("userName"));
                        user.setPassword((String) task.getResult().get("password"));
                        user.setMail((String) task.getResult().get("mail"));
                        user.setYearOfBirth((long) task.getResult().get("yearOfBirth"));
                        user.setGender((long) task.getResult().get("gender"));
                        user.setKey_pg((String) task.getResult().get("key_pg"));

                        //update user type
                        UserType userType;
                        if (task.getResult().get("userType").equals("citizen")) {
                            userType = UserType.citizen;
                        } else {
                            userType = UserType.parliament;
                        }
                        user.setUserType(userType);
                    }
                });
    }

    public static Task<DocumentSnapshot> getNamePG(String key_pg, String[] pg_name) {
        return db.collection("PoliticalGroups")
                .document(key_pg).get().addOnCompleteListener(task -> {
                    pg_name[0] = task.getResult().get("group_name").toString();
                });
    }

    public static void setCurrUser(User user) {
        db.collection("Users").document(user.getKey()).set(user);
    }
}
