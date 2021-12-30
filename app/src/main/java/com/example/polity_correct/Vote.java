package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Date;

public class Vote extends AppCompatActivity {

    private FirebaseFirestore db;

    private TextView txt_proposition_title, getTxt_proposition_description;
    private String proposition_key;
    private StatusVote user_choice;
    private double vote_grade;
    private Timestamp vote_date;
    private RadioGroup radioGroup;
    private RadioButton radioBtn;
    private RatingBar ratingbar;
    private User curr_user;
    private Citizen curr_citizen;
    private String user_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        curr_user = Login.getCurrUser();

        db = FirebaseFirestore.getInstance();

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעה");

        txt_proposition_title = (TextView) findViewById(R.id.propositionTitle);
        getTxt_proposition_description = (TextView) findViewById(R.id.propositionDetails);
        Intent i = getIntent();
        if (i != null) {
            Proposition p = (Proposition) i.getSerializableExtra("current proposition");
            proposition_key = p.getKey();
            txt_proposition_title.setText(p.getTitle());
            getTxt_proposition_description.setText(p.getDescription());
        }

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                user_token = token;
                Log.d("Token", "retrieve token successful : " + token);
            } else {
                Log.w("Token", "token should not be null...");
            }
        });
    }

    public void onClickSend(View view) {
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        radioGroup = (RadioGroup) findViewById(R.id.radiobtns);
        String rating = String.valueOf(ratingbar.getRating());
        vote_grade = Double.parseDouble(rating);

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioBtn = (RadioButton) findViewById(selectedId);

        String vote_string = (String) radioBtn.getText();
        switch (vote_string) {
            case "נגד":
                user_choice = StatusVote.against;
                break;
            case "נמנע":
                user_choice = StatusVote.impossible;
                break;
            case "בעד":
                user_choice = StatusVote.agreement;
                break;
            default:
                Toast.makeText(Vote.this,
                        "ההצבעה לא תקינה", Toast.LENGTH_SHORT).show();
        }

        //Extract vote date
        Date currentTime = Calendar.getInstance().getTime();
        vote_date = new Timestamp(currentTime);
        curr_citizen = new Citizen(curr_user.getUserName(), curr_user.getPassword(), curr_user.getMail(), curr_user.getYearOfBirth(), curr_user.getGender(), UserType.citizen, curr_user.getKey_pg());
        curr_citizen.Vote(curr_citizen, proposition_key, vote_grade, user_choice, vote_date);

        afterVoteNotification();

        Intent intent = new Intent(this, HomeCitizen.class);

        Toast.makeText(Vote.this, "ההצבעה " + radioBtn.getText() + " נשלחה", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void afterVoteNotification() {
        String title = "הצבעת השפעת!";
        String body = "הצבעתך " + radioBtn.getText() + " לחוק " + txt_proposition_title.getText() + "נקלטה בהצלחה. תודה!";
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(user_token, title, body, getApplicationContext(), Vote.this);
        notificationsSender.SendNotifications();
    }
}