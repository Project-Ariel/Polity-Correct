package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Results extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private CardView result;
    private TextView proposion_title;
    private ArrayList<Proposition> propositions;
    private Proposition curr_proposition;

    ArrayList<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("תוצאות האמת");

        dropdown = (Spinner) findViewById(R.id.spinnerProp);

        Intent i = getIntent();
        if (i != null) {
            System.out.println("in intent Result ");
            propositions = (ArrayList<Proposition>) i.getSerializableExtra("propositions");
            for (Proposition p : propositions) {
                if (p.wasVoted())
                    titles.add(p.getTitle());
            }
            System.out.println(titles.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_proposition=propositions.get(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("here");
    }

    public void openResultsView(View view) {
        result = (CardView) findViewById(R.id.result_view);
        result.setVisibility(View.VISIBLE);
        proposion_title= (TextView) findViewById(R.id.proposion_title);
        proposion_title.setText(curr_proposition.getTitle());
    }
}