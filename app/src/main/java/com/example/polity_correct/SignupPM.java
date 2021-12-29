package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SignupPM extends AppCompatActivity {
    String mail;
    FirebaseFirestore db;
    static private FirebaseAuth mAuth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_pm);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mail = ((TextView) findViewById(R.id.textUsermail)).getText().toString();
    }

    public void onClickOK(View view) {
        if (!mail.contains("@KNESSET.GOV.IL") && !mail.contains("@knesset.gov.il")){
            Toast.makeText(SignupPM.this, "This email is not belong to parliament member.", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if (task.isSuccessful()) {
                        SignInMethodQueryResult result = task.getResult();
                        List<String> signInMethods = result.getSignInMethods();
                        if (signInMethods.isEmpty()) {
                            Toast.makeText(SignupPM.this, "This email is not belong to parliament member.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // TODO: 12/29/2021
                            //אחרת- שולח למייל של הח"כ את הסיסמה שלו
                        }
                    }
                }
            });
            intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

//    new_user = new ParliamentMember("", pass, mail, 0000L, -1, UserType.parliament, "default");
//    next = new Intent(SignupCitizen.this, HomeParliament.class);
//                                    db.collection("Users").document().set(new_user);
//                                    Login.setCurr_user(new_user);
}