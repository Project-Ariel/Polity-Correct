package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeParliament extends AppCompatActivity {

    private ImageView logOut;
    private TextView accountMail;
    private Button changePass;
    private Button propositions;
    private Button statistics;
    private TextView connectUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_parliament);

        accountMail = (TextView) findViewById(R.id.userName);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            accountMail.setText(b.getString("AccountMail"));
        }

        //logout listener
        logOut = (ImageView) findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        //changePass page listener
        changePass = (Button) findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsPage();
            }
        });

        // propositions page listener
        propositions = (Button) findViewById(R.id.propositions);
        propositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPropositionsPage();
            }
        });

        // statistic page listener
        statistics = (Button) findViewById(R.id.statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatisticsPage();
            }
        });

        // connectUs listener
        connectUs = (TextView) findViewById(R.id.connectUs0);
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

    public void openStatisticsPage() {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    public void sendMail() {

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"Politycorrect@gmail.com"});


        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Note from mail :" + accountMail.getText().toString());

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Email Body..");

        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));
    }
}