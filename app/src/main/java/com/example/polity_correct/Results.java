package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Results extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private CardView result;
    private TextView proposition_title;
    private static ArrayList<Proposition> propositions= new ArrayList<>();
    private Proposition curr_proposition;

    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("תוצאות האמת");

        dropdown = (Spinner) findViewById(R.id.spinnerProp);

        DB.getPropositions(propositions).addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                if (p.wasVoted())
                    titles.add(p.getTitle());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(this);
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_proposition = propositions.get(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("here");
    }

    public void openResultsView(View view) {
        result = (CardView) findViewById(R.id.result_view);
        result.setVisibility(View.VISIBLE);
        proposition_title = (TextView) findViewById(R.id.proposition_title);
        proposition_title.setText(curr_proposition.getTitle());
    }
}