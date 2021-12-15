package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;


public class Signup extends AppCompatActivity {

    String mail, pass, pass_valid;
    Intent next;
    User new_user;

    FirebaseFirestore db;
    static private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mail= ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        pass= ((TextView) findViewById(R.id.new_password)).getText().toString();
        pass_valid= ((TextView) findViewById(R.id.new_password_valid)).getText().toString();

    }

    public void onClickOK(View view) {

        mail= ((TextView) findViewById(R.id.textUsermail)).getText().toString();
        pass= ((TextView) findViewById(R.id.new_password)).getText().toString();

        if (validateEmail() && validatePassword()) {
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                if (mail.contains("@KNESSET.GOV.IL") || mail.contains("@knesset.gov.il")){
                                    new_user= new ParliamentMember("",pass,mail,0000,-1,UserType.parliament,"default");
                                    next = new Intent(Signup.this, HomeParliament.class);
                                    db.collection("Users").document().set(new_user);
                                }
                                else{
                                    new_user= new Citizen("00000000","",pass,mail,0000,-1,UserType.citizen,"default");
                                    next = new Intent(Signup.this, UserDetails.class);
                                }
                                next.putExtra("user_obj",new_user);
                                startActivity(next);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Signup.this, "Signup failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    private boolean validateEmail() {
        String emailInput = ((TextView) findViewById(R.id.textUsermail)).getText().toString();;

        if (emailInput.isEmpty()) {
            Toast.makeText(Signup.this, "email can't be empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(Signup.this, "Please enter a valid email address.",
                    Toast.LENGTH_SHORT).show();
            return false;
//        }else if (_____) {
//            Toast.makeText(Signup.this, "This email is already exist.",
//                    Toast.LENGTH_SHORT).show();
//            return false;
        }else {
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = ((TextView)findViewById(R.id.new_password)).getText().toString();
        String ConfitmpasswordInput = ((TextView)findViewById(R.id.new_password_valid)).getText().toString();
        if (passwordInput.isEmpty()) {
            Toast.makeText(Signup.this, "Password can't be empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordInput.length()<6) {
            Toast.makeText(Signup.this, "Password must be at least 5 characters",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordInput.equals(ConfitmpasswordInput)) {
            Toast.makeText(Signup.this, "Password Would Not be matched",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(Signup.this, "Password Matched",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    }


}