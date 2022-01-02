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

import java.util.List;

public class SignupPM extends AppCompatActivity {
    String mail;
    static private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // TODO: 1/2/2022 auth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_pm);
    }

    public void onClickOK(View view) {
        mail = ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        if (!mail.contains("@KNESSET.GOV.IL") && !mail.contains("@knesset.gov.il")) {
            Toast.makeText(SignupPM.this, "מייל זה לא שייך לחבר כנסת", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if (task.isSuccessful()) {
                        SignInMethodQueryResult result = task.getResult();
                        List<String> signInMethods = result.getSignInMethods();
                        if (signInMethods.isEmpty()) {
                            Toast.makeText(SignupPM.this, "מייל זה לא שייך לחבר כנסת", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            auth.sendPasswordResetEmail(mail);
                            Toast.makeText(SignupPM.this, "נשלח אליך מייל לבחירת סיסמא", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            startActivity(new Intent(this, Login.class));
        }
    }
}