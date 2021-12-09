package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Vote extends AppCompatActivity {

    private TextView voteId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        voteId = (TextView) findViewById(R.id.voteId);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            voteId.setText("Vote ID:" + (b.getString("voteID")));
        }
    }
}