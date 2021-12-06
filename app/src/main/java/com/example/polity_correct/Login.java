package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText txtAccountID, txtPass;
    Button login;

    //FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    //    db = FirebaseFirestore.getInstance();
        txtAccountID = (EditText)findViewById(R.id.txtAccountID);
        txtPass = (EditText)findViewById(R.id.txtPass);

        //home page listener
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });
    }

    //open Home page
    public void onClickLogin() {
        Intent next = new Intent(Login.this,Home.class);
        Bundle b = new Bundle();
        b.putString("AccountID", txtAccountID.getText().toString());
        next.putExtras(b);
        startActivity(next);
    }
}