package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SignupCitizen extends AppCompatActivity {

    String mail, pass, pass_valid;
    Intent next;
    static User new_user;

    FirebaseFirestore db;
    static private FirebaseAuth mAuth;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_citizen);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mail = ((TextView) findViewById(R.id.textUsermail)).getText().toString();

        pass = ((TextView) findViewById(R.id.new_password)).getText().toString();
        pass_valid = ((TextView) findViewById(R.id.new_password_valid)).getText().toString();

    }

    public void onClickOK(View view) {

        mail = ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        pass = ((TextView) findViewById(R.id.new_password)).getText().toString();

        if (validateEmail() && validatePassword()) {
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                new_user = new Citizen("", pass, mail, 0000L, -1, UserType.citizen, "default");
                                next = new Intent(SignupCitizen.this, CitizenDetails.class);
                                next.putExtra("user_obj", new_user);
                                startActivity(next);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignupCitizen.this, "Signup failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    private boolean validateEmail() {

        if (mail.isEmpty()) {
            Toast.makeText(SignupCitizen.this, "email can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(SignupCitizen.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                flag=true;
                mAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();
                            if (!signInMethods.isEmpty()) {
                                Toast.makeText(SignupCitizen.this, "This email is already exist.", Toast.LENGTH_SHORT).show();
                                flag= false;
                            }
                        }
                    }});
                return flag;
            }
        }
    }

    private boolean validatePassword() {
        String passwordInput = ((TextView) findViewById(R.id.new_password)).getText().toString();
        String ConfitmpasswordInput = ((TextView) findViewById(R.id.new_password_valid)).getText().toString();
        if (passwordInput.isEmpty()) {
            Toast.makeText(SignupCitizen.this, "Password can't be empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordInput.length() < 6) {
            Toast.makeText(SignupCitizen.this, "Password must be at least 5 characters",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordInput.equals(ConfitmpasswordInput)) {
            Toast.makeText(SignupCitizen.this, "Password Would Not be matched",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(SignupCitizen.this, "Password Matched",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    }


}