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
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}