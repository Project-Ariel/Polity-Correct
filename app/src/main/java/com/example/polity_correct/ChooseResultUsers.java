package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class ChooseResultUsers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private ArrayList<String> titles = new ArrayList<>();
    private static ArrayList<Proposition> propositions = new ArrayList<>();
    private Proposition curr_proposition;
    private ParliamentMember curr_pm;
    private static double[] res;
    private User currUser = Login.getCurrUser();
    private static String[] name_curr_pg = new String[1];
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_result_users);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעות המשתמשים");

        curr_pm = new ParliamentMember(currUser.getUserName(), currUser.getPassword(), currUser.getMail(), currUser.getYearOfBirth(), currUser.getGender(), UserType.parliament, currUser.getKey_pg());

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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    startActivity(new Intent(this, HomeParliament.class));
                    break;
                case R.id.Statistics:
                    Intent intent = new Intent(this, ChooseResultUsers.class);
                    intent.putExtra("index_current_proposition", 0);
                    startActivity(intent);
                    break;
                case R.id.Propositions:
                    startActivity(new Intent(this, PropositionsParliament.class));
                    break;
                case R.id.UpdatePassword:
                    startActivity(new Intent(this, Settings.class));
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

    public void onClickAllUsers(View view) {
        res = new double[]{0, 0, 0, 0};
        curr_pm.show_citizen_votes(curr_proposition, res).addOnCompleteListener(task -> {
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra("proposition_title", curr_proposition.getTitle());
            intent.putExtra("pg", "כל המשתמשים");
            intent.putExtra("result", res);
            startActivity(intent);
        });
    }

    // TODO: 1/2/2022 array to String
    public void onClickSpecificPoliticalGroup(View view) {
        res = new double[]{0, 0, 0, 0};
        curr_pm.show_citizen_votes_specific_PG(curr_proposition, res).addOnCompleteListener(task -> {
            DB.getNamePG(currUser.getKey_pg(), name_curr_pg).addOnCompleteListener(task0 -> {
                Intent intent = new Intent(this, Statistics.class);
                intent.putExtra("pg", name_curr_pg[0]);
                intent.putExtra("proposition_title", curr_proposition.getTitle());
                intent.putExtra("result", res);
                startActivity(intent);
            });
        });
    }

}