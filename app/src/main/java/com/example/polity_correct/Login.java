package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText txtAccountMail, txtPass;
    Button login;
    Button register;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = FirebaseFirestore.getInstance();
        txtAccountMail = (EditText)findViewById(R.id.txtAccountMail);
        txtPass = (EditText)findViewById(R.id.txtPass);

        //home page listener
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });

        //register listener
        register = (Button) findViewById(R.id.signin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });
    }

    //open Home page
    public void onClickLogin() {
        Intent next = new Intent(Login.this, HomeCitizen.class);
        Bundle b = new Bundle();
        b.putString("AccountMail", txtAccountMail.getText().toString());
        b.putString("Pass", txtPass.getText().toString());
        next.putExtras(b);
        startActivity(next);
    }

    public void onClickRegister() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }


    public void updateDBOnClick(View view) {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("fullName", "my name");
        user.put("gender", "male");
        user.put("mail", txtAccountMail.getText().toString());
        user.put("password", txtPass.getText().toString());
        user.put("politicalGroupKey", "LoveMe");
        user.put("userType", 1);
        user.put("yearOfBirth", 1234);


        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
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