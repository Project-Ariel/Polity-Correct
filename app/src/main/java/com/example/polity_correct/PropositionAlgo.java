package com.example.polity_correct;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class PropositionAlgo {
    private HashMap<Long, Integer> res;
    private Proposition proposition;
    private String userPg;


    public PropositionAlgo(Proposition p) {
        this.proposition = p;
    }

    public Proposition getProposition() {
        return this.proposition;
    }

    public HashMap<Long, Integer> finalResult() {
        return this.finalResult(false);
    }

    public HashMap<Long, Integer> finalResult(boolean orderByPG) {
//        System.out.println("call create");
        createRes();

//        System.out.println("return res");
        return res;
    }

    public void createRes() {


        res = new HashMap<>();
        res.put(0L, 0); // Against
        res.put(1L, 0); // Impossible
        res.put(2L, 0); // Pro


        //        if (orderByPG) {
        //            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //
        //            DocumentReference us = FirebaseFirestore.getInstance().collection("Users").document(userID);
        //            us.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        //                @Override
        //                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        //                    if (task.isSuccessful()) {
        //                        DocumentSnapshot document = task.getResult();
        //                        String pg = (String) document.get("key_pg");
        //                        userPg = pg;
        //                        System.out.println("userPg="+userPg);
        //                    } else {
        //
        //                    }
        //                }
        //            });
        //        }


        FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", proposition.getKey())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        System.out.println("start onComplete");
                        if (task.isSuccessful()) {
//                            System.out.println("start isSuccessful");
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                long userChoice = (long) document.get("user_choice");
                                res.put(userChoice, res.get(userChoice) + 1);

                            }

//                            System.out.println("into for " + res);
//                            System.out.println("end isSuccessful");
                        } else {
                        }
//                        System.out.println("end onComplete");

                    }
                });


    }
}

