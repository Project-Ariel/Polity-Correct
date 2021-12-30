package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText txtAccountMail, txtPass;
    private String mail, pass;
    private String userID;
    private FirebaseFirestore db;
    static private User curr_user;
    static private FirebaseAuth mAuth;
    private CollectionReference databaseReference;
    private DocumentSnapshot document;
    private long gen;
    private UserType userTypeTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            FirebaseAuth.getInstance().signOut();
        }

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        txtAccountMail = (EditText) findViewById(R.id.textUsermail_login);
        txtPass = (EditText) findViewById(R.id.textPassword_login);
        databaseReference = db.collection("Users");
    }

    //open Home page
    public void onClickLogin(View view) {
        mail = txtAccountMail.getText().toString();
        pass = txtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        // Sign in success, create user object and update UI with the signed-in user's information
                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        databaseReference.document(userID).get().addOnCompleteListener(task0 -> {
                            if (task0.isSuccessful()) {
                                document = task0.getResult();

                                //update user gender
                                gen = -1;
                                if (document.get("gender") != null) {
                                    gen = (long) document.get("gender");
                                }

                                //update user type
                                if (document.get("userType").equals("citizen")) {
                                    userTypeTemp = UserType.citizen;
                                } else {
                                    userTypeTemp = UserType.parliament;
                                }

                                //create new user
                                curr_user = new User((String) document.get("userName"),
                                        (String) document.get("password"),
                                        (String) document.get("mail"),
                                        (long) document.get("yearOfBirth"),
                                        gen,
                                        userTypeTemp,
                                        (String) document.get("key_pg"));

                                Intent next;
                                if (curr_user.getUserType().name().equals(UserType.parliament.name())) {
                                    //if user is parliament member
                                    next = new Intent(Login.this, HomeParliament.class);
                                } else {
                                    //else- user is citizen
                                    next = new Intent(Login.this, HomeCitizen.class);
                                }
                                startActivity(next);
                            }
                        });
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Login.this, "ההתחברות נכשלה",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static User getCurrUser() {
        return curr_user;
    }

    public static void setCurr_user(User temp) {
        curr_user = temp;
    }

    @Override
    public void onBackPressed() {

    }

    public void onClickSignupCitizen(View view) {
        startActivity(new Intent(this, SignupCitizen.class));
    }

    public void onClickSignupPM(View view) {
        startActivity(new Intent(this, SignupPM.class));
    }
}

