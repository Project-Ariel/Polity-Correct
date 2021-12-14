package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText txtAccountMail, txtPass;
    String mail, pass;

    FirebaseFirestore db;
    static private FirebaseAuth mAuth;

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
    }

    //open Home page
    public void onClickLogin(View view) {
        mail = txtAccountMail.getText().toString();
        pass = txtPass.getText().toString();

        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent next = new Intent(Login.this, HomeCitizen.class);

                            if (mail.contains("@KNESSET.GOV.IL") || mail.contains("@knesset.gov.il")) {
                                //if user is parliament member
                                next = new Intent(Login.this, HomeParliament.class);
                            } else {
                                //else- user is citizen
                                next = new Intent(Login.this, HomeCitizen.class);
                            }

                            Bundle b = new Bundle();
                            b.putString("AccountMail", txtAccountMail.getText().toString());
                            b.putString("Pass", txtPass.getText().toString());
                            next.putExtras(b);
                            startActivity(next);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "ההתחברות נכשלה",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}