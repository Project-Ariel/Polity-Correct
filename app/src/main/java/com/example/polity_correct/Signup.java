package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    Button register;
    String mail, pass;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mail= ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        pass= ((TextView) findViewById(R.id.new_password)).getText().toString();
        register = (Button) findViewById(R.id.OK);

    }

    public void onClickOK(View view) {
        Intent next;
        Bundle b = new Bundle();
        b.putString("AccountMail",mail);
        b.putString("Pass", pass);
        if(mail.contains("@KNESSET.GOV.IL")||mail.contains("@knesset.gov.il")){
           //if user is parliament member
            next = new Intent(Signup.this, HomeParliament.class);
        }
        else{
            //else- user is citizen
            next = new Intent(Signup.this,UserDetails.class);
        }
        next.putExtras(b);
        startActivity(next);
    }
}