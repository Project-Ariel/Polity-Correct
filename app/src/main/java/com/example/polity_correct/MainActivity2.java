package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle b = getIntent().getExtras();
        String AccountID = "-1";
        String Pass = null;
        if(b != null){
            AccountID = b.getString("AccountID");
            Pass = b.getString("Pass");
            System.out.println("\nSuccess move to next windows with user account: "+AccountID+" and pass:"+Pass+"\n");
        }

    }
}