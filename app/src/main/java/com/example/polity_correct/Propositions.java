package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Propositions extends AppCompatActivity {

    private ImageView logo;
    private Button[] votesButton;

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

        //vote listener
        int[] ids = new int[]{R.id.voteBtn0, R.id.voteBtn1, R.id.voteBtn2, R.id.voteBtn3, R.id.voteBtn4, R.id.voteBtn5, R.id.voteBtn6,
                R.id.voteBtn7, R.id.voteBtn8, R.id.voteBtn9, R.id.voteBtn10, R.id.voteBtn11};
        votesButton = new Button[ids.length];
        for (int i = 0; i < votesButton.length; i++) {
            votesButton[i] = (Button) findViewById(ids[i]);
            int finalI = i;
            votesButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickVote(finalI);
                }
            });
        }
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeCitizen.class);
        startActivity(intent);
    }

    public void onClickVote(int id) {
        Intent next = new Intent(this, Vote.class);
        Bundle b = new Bundle();
        b.putString("voteID", String.valueOf(id));
        next.putExtras(b);
        startActivity(next);
    }
}