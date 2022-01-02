package com.example.polity_correct;

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

    // TODO: 1/2/2022 DB
    private Spinner dropdown;
    private ArrayList<String> titles = new ArrayList<>();
    private static ArrayList<Proposition> propositions = new ArrayList<>();
    private Proposition curr_proposition;
    private static int[] res;
    private User currUser = Login.getCurrUser();
    private String voteKeyPG;
    private static String[] name_curr_pg= new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_result_users);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות המשתמשים");

        dropdown = (Spinner) findViewById(R.id.chooseProp);

        DB.getPropositions(propositions).addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                if (!p.wasVoted())
                    titles.add(p.getTitle());
            }

            Intent i = getIntent();
            int index_curr_prop = (int) i.getExtras().get("index_current_proposition");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setSelection(index_curr_prop);
            dropdown.setOnItemSelectedListener(this);
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_proposition = propositions.get(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onClickAllUsers(View view) {
        res = new int[]{0, 0, 0};
        DB.getPropVotes(res, curr_proposition.getKey()).addOnCompleteListener(task -> {
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra("proposition_title", curr_proposition.getTitle());
            intent.putExtra("pg", "כל המשתמשים");
            intent.putExtra("result", res);
            startActivity(intent);
        });
    }

    // TODO: 1/2/2022 array to String
    public void onClickSpecificPoliticalGroup(View view) {
        res = new int[]{0, 0, 0};
        getVotesFromDBSpecificPG().addOnCompleteListener(task -> {
            DB.getNamePG(currUser.getKey_pg(), name_curr_pg).addOnCompleteListener(task0 -> {
                Intent intent = new Intent(this, Statistics.class);
                intent.putExtra("pg", name_curr_pg[0]);
                intent.putExtra("proposition_title", curr_proposition.getTitle());
                intent.putExtra("result", res);
                startActivity(intent);
            });
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
                                        case "abstain":
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