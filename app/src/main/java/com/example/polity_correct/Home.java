package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private ImageView logOut;
    private TextView accountID;
    private Button settings;
    private Button propositions;
    private Button results;
    private TextView connectUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        accountID = (TextView) findViewById(R.id.userName);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            accountID.setText(b.getString("AccountID"));
        }

        //logout listener
        logOut = (ImageView) findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        //settings page listener
        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsPage();
            }
        });

        // propositions page listener
        propositions = (Button) findViewById(R.id.vote_button);
        propositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPropositionsPage();
            }
        });

        // results page listener
        results= (Button) findViewById(R.id.results);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResultsPage();
            }
        });

        // connectUs listener
        connectUs = (TextView) findViewById(R.id.connectUs);
        connectUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openPropositionsPage() {
        Intent intent = new Intent(this, Propositions.class);
        startActivity(intent);
    }

    public void openResultsPage() {
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

    public void sendMail() {

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "Politycorrect@gmail.com" });

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Not from accountID=" + accountID.getText().toString());

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Email Body..");

        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));


    }
}