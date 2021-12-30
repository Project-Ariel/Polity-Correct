package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeParliament extends AppCompatActivity {

    public static ArrayList<Proposition> propositions = new ArrayList<>();
    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_parliament);

        user_name = (TextView) findViewById(R.id.userName);
        user_name.setText(Login.getCurrUser().getUserName());

//
//        //get propositions from DB
//        FirebaseFirestore.getInstance().collection("Propositions")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), false);
//                                propositions.add(p);
//                            }
//                        }
//                    }
//                });
    }

    private Task<QuerySnapshot> getNotVotedPropositionsFromDB() {
        return FirebaseFirestore.getInstance().collection("Propositions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), (boolean) document.get("voted"));
                            if (!p.wasVoted()) {
                                propositions.add(p);
                            }
                        }
                    }
                });
    }

    public void openSettingsPage(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openPropositionsPage(View view) {
        Intent intent = new Intent(this, PropositionsParliament.class);
        getNotVotedPropositionsFromDB().addOnCompleteListener(task -> {
            intent.putExtra("propositions", propositions);
            startActivity(intent);
        });
    }

    public void openStatisticsPage(View view) {
        Intent intent = new Intent(this, ChooseResultUsers.class);
        getNotVotedPropositionsFromDB().addOnCompleteListener(task -> {
            intent.putExtra("propositions", propositions);
            intent.putExtra("index_current_proposition", 0);
            startActivity(intent);
        });
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