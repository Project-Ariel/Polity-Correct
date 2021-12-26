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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseResultUsers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner dropdown;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Proposition> propositions;
    private Proposition curr_proposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_result_users);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות המשתמשים");

        dropdown = (Spinner) findViewById(R.id.chooseProp);

        Intent i = getIntent();
        if (i != null) {
            propositions = (ArrayList<Proposition>) i.getSerializableExtra("propositions");
            for (Proposition p : propositions) {
                titles.add(p.getTitle());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        System.out.println(parent.getItemAtPosition(position).toString());
        curr_proposition = propositions.get(position);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("here");
    }

    public void onClickAllUsers(View view) {
        int [] res = {0,0,0}; // [Against, Impossible, Pro]

        Intent intent = new Intent(this, Statistics.class);
        FirebaseFirestore.getInstance().collection("Votes")
                .whereEqualTo("proposition_key", curr_proposition.getKey())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                long userChoice = (long) document.get("user_choice");
                                res[(int)userChoice]++;

                            }
                            intent.putExtra("proposition_title", curr_proposition.getTitle());
                            intent.putExtra("pg", "כל המשתמשים");
                            intent.putExtra("result", res);

                            startActivity(intent);
                        }
                    }
                });


    }

    public void onClickSpecificPoliticalGroup(View view) {
        Intent intent = new Intent(this, Statistics.class);
        intent.putExtra("pg", "specific");

        startActivity(intent);
    }
}