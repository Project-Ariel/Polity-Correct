package com.example.polity_correct;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeParliament extends AppCompatActivity {

    private TextView user_name;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_parliament);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        user_name = (TextView) findViewById(R.id.userName);
        user_name.setText(Login.getCurrUser().getUserName());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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