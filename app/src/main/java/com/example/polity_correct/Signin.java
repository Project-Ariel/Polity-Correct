package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Signin extends AppCompatActivity {
    Button register;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        mail= ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        register = (Button) findViewById(R.id.register);

        if (mail.contains("1")){
            register.setText("אישור");
        }
        else {
            register.setText("המשך");
        }

    }
}