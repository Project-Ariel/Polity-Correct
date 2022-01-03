package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeParliament extends AppCompatActivity {

    public static ArrayList<Proposition> propositions = new ArrayList<>();
    private TextView user_name;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_parliament);
        user_name = (TextView) findViewById(R.id.userName);
        user_name.setText(Login.getCurrUser().getUserName());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Statistics:
                    Intent intent = new Intent(this, ChooseResultUsers.class);
                    intent.putExtra("index_current_proposition", 0);
                    startActivity(intent);
                    break;
                case R.id.Propositions:
                    startActivity(new Intent(HomeParliament.this, PropositionsParliament.class));
                    break;
                case R.id.UpdatePassword:
                    startActivity(new Intent(HomeParliament.this, Settings.class));
                    break;
                case R.id.LogOut:
                    startActivity(new Intent(HomeParliament.this, Login.class));
                    break;
                default:
                    break;
            }
            return false;
        });
    }

//    private Task<QuerySnapshot> getNotVotedPropositionsFromDB() {
//        return FirebaseFirestore.getInstance().collection("Propositions")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), (boolean) document.get("voted"));
//                            if (!p.wasVoted()) {
//                                propositions.add(p);
//                            }
//                        }
//                    }
//                });
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openSettingsPage(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openPropositionsPage(View view) {
        startActivity(new Intent(this, PropositionsParliament.class));
    }

    public void openStatisticsPage(View view) {
        Intent intent = new Intent(this, ChooseResultUsers.class);
        intent.putExtra("index_current_proposition", 0);
        startActivity(intent);
    }

    public void sendMail(View view) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"Politycorrect@gmail.com"});


        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Note from mail :" + user_name.getText().toString());

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Email Body..");

        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));
    }

    public void openLoginPage(View view) {
        FirebaseAuth.getInstance().signOut(); // sign out a user
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}