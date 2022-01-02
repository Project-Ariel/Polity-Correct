package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText txtAccountMail, txtPass;
    private String mail, pass;
    private String userID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static User curr_user = new User();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            FirebaseAuth.getInstance().signOut();
        }

        txtAccountMail = (EditText) findViewById(R.id.text_usermail_login);
        txtPass = (EditText) findViewById(R.id.textPassword_login);
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

                        DB.getUser(userID, curr_user).addOnCompleteListener(task1 -> {

                            //Update user document in reset password case
                            if (!curr_user.getPassword().equals(pass)) {
                                db.collection("Users").document(userID).set(curr_user);
                            }

                            if (curr_user.getUserType().name().equals(UserType.parliament.name())) {
                                //if- user is parliament member
                                startActivity(new Intent(Login.this, HomeParliament.class));
                            } else {
                                //else- user is citizen
                                startActivity(new Intent(Login.this, HomeCitizen.class));
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

