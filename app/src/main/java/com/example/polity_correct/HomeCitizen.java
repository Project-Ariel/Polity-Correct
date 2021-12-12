package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeCitizen extends AppCompatActivity {

    private TextView accountMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_citizen);

        accountMail = (TextView) findViewById(R.id.userName);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            accountMail.setText(b.getString("AccountMail"));
        }
    }

    public void openLoginPage(View view) {
        FirebaseAuth.getInstance().signOut(); // sign out a user
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openSettingsPage(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openPropositionsPage(View view) {
        Intent intent = new Intent(this, Propositions.class);
        startActivity(intent);
    }

    public void openResultsPage(View view) {
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

    public void sendMail(View view) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "Politycorrect@gmail.com" });

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Note from mail :" + accountMail.getText().toString());

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Email Body..");

        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));
    }
}