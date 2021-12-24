package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Vote extends AppCompatActivity {

    private FirebaseFirestore db;

    private TextView txt_proposition_title, getTxt_proposition_description;
    private String user_id, proposition_key;
    private int user_choice;
    private double vote_grade;
    private Timestamp vote_date;

    private RadioGroup radioGroup;
    private RadioButton radioBtn;
    private RatingBar ratingbar;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        db = FirebaseFirestore.getInstance();

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעה");

        addListenerOnButton();

        txt_proposition_title = (TextView) findViewById(R.id.propositionTitle);
        getTxt_proposition_description = (TextView) findViewById(R.id.propositionDetails);
        Intent i = getIntent();
        if (i != null) {
            Proposition p = (Proposition) i.getSerializableExtra("current proposition");
            proposition_key = p.getKey();
            txt_proposition_title.setText(p.getTitle());
            getTxt_proposition_description.setText(p.getDescription());
        }
    }

    //Extract user choice & Extract vote grade
    private void addListenerOnButton() {
        Intent intent = new Intent(this, HomeCitizen.class);

        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        radioGroup = (RadioGroup) findViewById(R.id.radiobtns);
        btnSend = (Button) findViewById(R.id.sendVote);

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String rating = String.valueOf(ratingbar.getRating());
                vote_grade = Double.parseDouble(rating);

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioBtn = (RadioButton) findViewById(selectedId);

                String vote_string = (String) radioBtn.getText();
                switch (vote_string) {
                    case "נגד":
                        System.out.println("נגד");
                        user_choice = 0;
                        break;
                    case "נמנע":
                        System.out.println("נמנע");
                        user_choice = 1;
                        break;
                    case "בעד":
                        System.out.println("בעד");
                        user_choice = 2;
                        break;
                    default:
                        Toast.makeText(Vote.this,
                                "ההצבעה לא תקינה", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Vote.this,
                        "ההצבעה " + radioBtn.getText() + " נשלחה", Toast.LENGTH_SHORT).show();

                //Extract user id
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                user_id = user.getUid();

                //Extract vote date
                Date currentTime = Calendar.getInstance().getTime();
                vote_date = new Timestamp(currentTime);

                updateDB();

                startActivity(intent);
            }
        });
    }

    public void onClickSend(View view) {
    }

    public void updateDB() {
        // Create a new user with a first and last name
        Map<String, Object> vote = new HashMap<>();
        vote.put("user_id", user_id);
        vote.put("proposition_key", proposition_key);
        vote.put("user_choice", user_choice);
        vote.put("vote_grade", vote_grade);
        vote.put("vote_date", vote_date);


        // Add a new document with a generated ID
        db.collection("Votes")
                .add(vote)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    private static final String TAG = " ";

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    private final Object TAG = " ";

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w((String) TAG, "Error adding document", e);
                    }
                });
    }
}