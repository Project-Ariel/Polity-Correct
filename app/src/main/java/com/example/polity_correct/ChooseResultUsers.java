package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChooseResultUsers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner dropdown;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Proposition> propositions;
    private Proposition curr_proposition;
    private int[] res;
    private User currUser = Login.getCurrUser();
    private String name_curr_pg, voteKeyPG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_result_users);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות המשתמשים");

        dropdown = (Spinner) findViewById(R.id.chooseProp);

        Intent i = getIntent();
        int index_curr_prop = 0;
        if (i != null) {
            propositions = (ArrayList<Proposition>) i.getSerializableExtra("propositions");
            for (Proposition p : propositions) {
                titles.add(p.getTitle());
            }
            index_curr_prop = (int) i.getExtras().get("index_current_proposition");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(index_curr_prop);
        dropdown.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_proposition = propositions.get(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onClickAllUsers(View view) {
        getVotesFromDB().addOnCompleteListener(task -> {
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra("proposition_title", curr_proposition.getTitle());
            intent.putExtra("pg", "כל המשתמשים");
            intent.putExtra("result", res);
            startActivity(intent);
        });
    }

    public void onClickSpecificPoliticalGroup(View view) {
        getVotesFromDBSpecificPG().addOnCompleteListener(task -> {
            getNamePoliticalGroup().addOnCompleteListener(task0 -> {
                Intent intent = new Intent(this, Statistics.class);
                intent.putExtra("pg", name_curr_pg);
                intent.putExtra("proposition_title", curr_proposition.getTitle());
                intent.putExtra("result", res);
                startActivity(intent);
            });
        });
    }

    private Task<DocumentSnapshot> getNamePoliticalGroup() {
        return FirebaseFirestore.getInstance().collection("PoliticalGroups")
                .document(currUser.getKey_pg())
                .get().addOnCompleteListener(task -> {
                    name_curr_pg = task.getResult().get("group_name").toString();
                });
    }

    private Task<QuerySnapshot> getVotesFromDB() {
        res = new int[]{0, 0, 0};
        return FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", curr_proposition.getKey())
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
                                case "impossible":
                                    choice = 1;
                                    break;
                                case "agreement":
                                    choice = 2;
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + userChoice);
                            }
                            res[choice]++;
                        }
                    }
                });
    }

    private Task<DocumentSnapshot> getKeyPoliticalGroup(String id_user) {
        return FirebaseFirestore.getInstance().collection("Users")
                .document(id_user)
                .get().addOnCompleteListener(task -> {
                    voteKeyPG = task.getResult().get("key_pg").toString();
                });
    }

    private Task<QuerySnapshot> getVotesFromDBSpecificPG() {
        res = new int[]{0, 0, 0};
        return FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", curr_proposition.getKey())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String voteUserId = (String) document.get("user_id");
                            //get key_pg of user vote
                            getKeyPoliticalGroup(voteUserId).addOnCompleteListener(task1 -> {
                                if (voteKeyPG.equals(currUser.getKey_pg())) {
                                    String userChoice = (String) document.get("user_choice");
                                    int choice;
                                    switch (userChoice) {
                                        case "against":
                                            choice = 0;
                                            break;
                                        case "impossible":
                                            choice = 1;
                                            break;
                                        case "agreement":
                                            choice = 2;
                                            break;
                                        default:
                                            throw new IllegalStateException("Unexpected value: " + userChoice);
                                    }
                                    res[choice]++;
                                }
                            });
                        }
                    }
                });
    }
}