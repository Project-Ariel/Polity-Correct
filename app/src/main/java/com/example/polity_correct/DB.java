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
import java.util.Objects;

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

    public static Task<QuerySnapshot> getPropositions(ArrayList<Proposition> propositions) {
        return db.collection("Propositions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), (boolean) document.get("voted"));
                            propositions.add(p);
                        }
                        Collections.sort(propositions);
                    }
                });
    }

    public static void updateVote(String proposition_key, double grade, StatusVote status, Timestamp date, String user_pg) {
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", mAuth.getCurrentUser().getUid());
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", status);
        vote.put("vote_grade", grade);
        vote.put("vote_date", date);
        vote.put("user_key_pg", user_pg);

        db.collection("Votes").add(vote);
    }

    public static Task<QuerySnapshot> getVotesSpecificPG(ArrayList<String> votes, ArrayList<Double> grades, String prop_key) {
        return db.collection("Votes")
                .whereEqualTo("proposition_key", prop_key)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //get key_pg of user vote
                            //TODO - add keyPG to Vote function and delete getKeyPoliticalGroup()
                            String voteKeyPG = (String) document.get("user_key_pg");
                            if (voteKeyPG.equals(Login.getCurrUser().getKey_pg())) {
                                votes.add((String) document.get("user_choice"));
                                grades.add(Double.parseDouble("" + document.get("vote_grade").toString()));
                            }
                        }
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

    static HashMap<String, HashMap<String, String>> membersVotes = new HashMap<String, HashMap<String, String>>();

    public static void getMembersVotes() {
        membersVotes = new HashMap<String, HashMap<String, String>>();
        db.collection("MembersVotes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, String> temp = membersVotes.get(document.get("proposition_key").toString());
                            if (temp == null) {
                                temp = new HashMap<String, String>();
                            }
                            temp.put(document.get("user_id").toString(), document.get("choice").toString());
                            membersVotes.put(document.get("proposition_key").toString(), temp);
                        }
                    }
                });

    }

    static HashMap<String, String> memberNames = new HashMap<String, String>();

    public static void getMemberNames() {
        memberNames = new HashMap<String, String>();
        db.collection("Users")
                .whereEqualTo("userType", "parliament")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            memberNames.put((String) document.getId(), document.get("userName").toString());
                        }
                    }
                });

    }

    static HashMap<String, String> propMap = new HashMap<String, String>();

    public static void getPropVoted() {
        propMap = new HashMap<String, String>();
        db.collection("Propositions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            propMap.put((String) document.getId(), document.get("voted").toString());
                        }
                    }
                });

    }

    //This function create a data structures that help to match a parialement mamber to the citizen.
    public static Task<QuerySnapshot> setVotesForAlgo(ArrayList<Integer> votes_user, ArrayList<Double> rank, HashMap<String, int[]> votes_pm, ArrayList<UserVote> all_user_votes) {

        return setUserVote(all_user_votes).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Collections.sort(all_user_votes, new SortByGrade());

                for (String name : memberNames.keySet()) {
                    votes_pm.put(name, new int[all_user_votes.size()]);
                }
                for (int i = 0; i < all_user_votes.size(); i++) {
                    votes_user.add(i,all_user_votes.get(i).getVote());
                    rank.add(i,all_user_votes.get(i).getRate());

                    for (String id : memberNames.keySet()) {
                        String vote = "abstain";
                        HashMap<String, String> v = membersVotes.get(all_user_votes.get(i).rule_id);

                        if (v == null) {
                            vote = "abstain";
                        } else {
                            vote = v.get(id);
                        }

                        if (vote == null) {
                            vote = "abstain";
                        }

                        if (vote.equals("abstain"))
                            Objects.requireNonNull(votes_pm.get(id))[i] = 1;
                        else if (vote.equals("against"))
                            Objects.requireNonNull(votes_pm.get(id))[i] = 0;
                        else
                            Objects.requireNonNull(votes_pm.get(id))[i] = 2;

                    }
                }
            }
        });
    }

    //create array list of all the user votes to proposition that accepted already
    public static Task<QuerySnapshot> setUserVote(ArrayList<UserVote> arr) {
        return db.collection("Votes")
                .whereEqualTo("user_id", mAuth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            UserVote v = new UserVote((String) document.get("proposition_key"), (String) document.get("user_choice"), (Double) document.get("vote_grade"));
                            String flag = propMap.get((String) document.get("proposition_key"));
                            if (flag != null) {
                                if (flag.equals("true")) {
                                    v.setAccepted(true);
                                    arr.add(v);
                                }
                            }
                        }
                    }
                });
    }
}