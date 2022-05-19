package com.example.polity_correct;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Results extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private static ArrayList<Proposition> propositions;
    private Proposition curr_proposition;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות האזרחים");

        propositions = new ArrayList<>();

        dropdown = (Spinner) findViewById(R.id.spinnerProp);

        DB.getPropositions(propositions).addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                titles.add(p.getTitle());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(this);
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    startActivity(new Intent(this, HomeCitizen.class));
                    break;
                case R.id.UpdateDetails:
                    startActivity(new Intent(this, Settings.class));
                    break;
                case R.id.Vote:
                    startActivity(new Intent(this, PropositionsCitizen.class));
                    break;
                case R.id.Results:
                    startActivity(new Intent(this, Results.class));
                    break;
                case R.id.Algo:
                    startActivity(new Intent(this, MatchParliament.class));
                    break;
                case R.id.LogOut:
                    startActivity(new Intent(this, Login.class));
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_proposition = propositions.get(position);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void openResultsView(View view) {
        ArrayList<String> votes = new ArrayList<>();
        ArrayList<Double> grades = new ArrayList<>();
        DB.getPropVotes(votes, grades, curr_proposition.getKey()).addOnCompleteListener(task -> {
            double[] res = Algo.calculate_votes_grades(votes, grades);
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra("proposition_title", curr_proposition.getTitle());
            intent.putExtra("pg", "כל המשתמשים");
            intent.putExtra("result", res);
            intent.putExtra("user","Citizen");
            startActivity(intent);
        });
    }
}