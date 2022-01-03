package com.example.polity_correct;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    public static Task<QuerySnapshot> getPropositions(ArrayList<Proposition> propositions) {
        return db.collection("Propositions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), (boolean) document.get("voted"));
                            propositions.add(p);
                        }
                    }
                });
    }

    public static Task<QuerySnapshot> getPropVotes(int[] votes, String prop_key) {
        return db.collection("Votes")
                .whereEqualTo("proposition_key", prop_key)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userChoice = (String) document.get("user_choice");
                            int choice;
                            switch (userChoice) {
                                case "against":
                                    choice = 0;
                                    break;
                                case "abstain":
                                    choice = 1;
                                    break;
                                case "agreement":
                                    choice = 2;
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + userChoice);
                            }
                            votes[choice]++;
                        }
                    }
                });
    }

    //send to function new user init user=new User()
    public static Task<DocumentSnapshot> getUser(String id, User user) {
        return db.collection("Users").document(id).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setUserName((String) task.getResult().get("userName"));
                        user.setPassword((String) task.getResult().get("password"));
                        user.setMail((String) task.getResult().get("mail"));
                        user.setYearOfBirth((long) task.getResult().get("yearOfBirth"));
                        user.setGander((long) task.getResult().get("gender"));
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

//    public static Task<DocumentSnapshot> getUserKeyPG(String id_user, String[] vote_key_pg) {
//        return db.collection("Users")
//                .document(id_user).get().addOnCompleteListener(task -> {
//                    vote_key_pg[0] = task.getResult().get("key_pg").toString();
//                });
//    }

    public static void setCurrUser(User user) {
        String id = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(id).set(user);
    }
}
