package com.example.polity_correct;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Date;

public class Vote extends AppCompatActivity {

    private TextView txt_proposition_title, getTxt_proposition_description;
    private String proposition_key;
    private StatusVote user_choice;
    private RadioGroup radioGroup;
    private RadioButton radioBtn;
    private RatingBar ratingbar;
    private User curr_user;
    private String user_token;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        curr_user = Login.getCurrUser();

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הצבעה");

        txt_proposition_title = (TextView) findViewById(R.id.propositionTitle);
        getTxt_proposition_description = (TextView) findViewById(R.id.propositionDetails);
        Intent i = getIntent();
        if (i != null) {
            Proposition p = (Proposition) i.getSerializableExtra("current proposition");
            proposition_key = p.getKey();
            txt_proposition_title.setText(p.getTitle());
            getTxt_proposition_description.setText("להסבר על החוק לחץ כאן");
            getTxt_proposition_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl(p.getDescription());
                }
            });

        }

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                user_token = token;
                Log.d("Token", "retrieve token successful : " + token);
            } else {
                Log.w("Token", "token should not be null...");
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    startActivity(new Intent(this, HomeCitizen.class));
                    break;
                case R.id.UpdateDetails:
                    startActivity(new Intent(this, Settings.class));
                    break;
                case R.id.Vote:
                    startActivity(new Intent(this, PropositionsCitizen.class));
                    break;
                case R.id.Results:
                    startActivity(new Intent(this, Results.class));
                    break;
                case R.id.Algo:
                    startActivity(new Intent(this, MatchParliament.class));
                    break;
                case R.id.LogOut:
                    startActivity(new Intent(this, Login.class));
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickSend(View view) {
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        radioGroup = (RadioGroup) findViewById(R.id.radiobtns);
        String rating = String.valueOf(ratingbar.getRating());
        double vote_grade = Double.parseDouble(rating);

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
                user_choice = StatusVote.abstain;
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
        Timestamp vote_date = new Timestamp(currentTime);
        Citizen curr_citizen = new Citizen(curr_user.getUserName(), curr_user.getPassword(), curr_user.getMail(), curr_user.getYearOfBirth(), curr_user.getGender(), UserType.citizen, curr_user.getKey_pg());
        curr_citizen.Vote(proposition_key, vote_grade, user_choice, vote_date, curr_citizen.getKey_pg());

        afterVoteNotification();

        Toast.makeText(Vote.this, "ההצבעה " + radioBtn.getText() + " נשלחה", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HomeCitizen.class));
    }

    private void afterVoteNotification() {
        String title = "הצבעת השפעת!";
        String body = "הצבעתך " + radioBtn.getText() + " לחוק " + txt_proposition_title.getText() + "נקלטה בהצלחה. תודה!";
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(user_token, title, body, getApplicationContext(), Vote.this);
        notificationsSender.SendNotifications();
    }
}