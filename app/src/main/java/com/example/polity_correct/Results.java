package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

public class Results extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private CardView result;
    private TextView proposition_title;
    private static ArrayList<Proposition> propositions = new ArrayList<>();
    private Proposition curr_proposition;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        result = (CardView) findViewById(R.id.result_view);
        result.setVisibility(View.VISIBLE);
        proposition_title = (TextView) findViewById(R.id.proposition_title);
        proposition_title.setText(curr_proposition.getTitle());
    }
}