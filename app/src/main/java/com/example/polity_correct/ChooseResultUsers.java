package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseResultUsers extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner dropdown;
    ArrayList<String> titles= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_result_users);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות המשתמשים");

        dropdown = (Spinner) findViewById(R.id.chooseProp);

        Intent i = getIntent();
        if (i != null) {
            ArrayList<Proposition> propositions = (ArrayList<Proposition>) i.getSerializableExtra("propositions");
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
    }

    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("here");
    }

    public void onClickAllUsers(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    public void onClickSpecificPoliticalGroup(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }
}