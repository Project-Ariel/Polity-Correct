package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText txtAccountID, txtPass;
    //FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    db = FirebaseFirestore.getInstance();
        txtAccountID = (EditText)findViewById(R.id.txtAccountID);
        txtPass = (EditText)findViewById(R.id.txtPass);
        String AccountID = txtAccountID.toString();
        String pass = txtPass.toString();

    }

    public void onClickLogin(View view) {
        Intent next = new Intent(MainActivity.this,MainActivity2.class);
        Bundle b = new Bundle();
        b.putString("AccountID", ((EditText)findViewById(R.id.txtAccountID)).toString());
        b.putString("Pass", txtPass.toString());
        next.putExtras(b);
        startActivity(next);
    }
}