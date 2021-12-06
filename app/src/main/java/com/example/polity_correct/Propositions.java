package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Propositions extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions);

        // logo listener
        logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}