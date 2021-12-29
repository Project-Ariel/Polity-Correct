package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText txtAccountMail, txtPass;
    String mail, pass;
    String userID;
    FirebaseFirestore db;
    static User curr_user;
    static private FirebaseAuth mAuth;
    CollectionReference databaseReference;
    Intent next;
    DocumentSnapshot document;
    int gen;
    UserType userTypeTemp;

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
        databaseReference=db.collection("Users");
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
                            // Sign in success, create user object and update UI with the signed-in user's information
                            userID=mAuth.getCurrentUser().getUid();
                            assert userID!=null;
                            databaseReference.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        document = task.getResult();
                                        //if the gender is null
                                        if(
                                                document.get("gender")!=null){gen=(int) document.get("gender");}
                                        else {
                                            gen=-1;}

                                        //update user type
                                        if(document.get("userType").equals("citizen")){
                                            userTypeTemp=UserType.citizen;
                                        }
                                        else{
                                            userTypeTemp=UserType.parliament;}

                                        //create new user
                                        curr_user = new User((String) document.get("userName"),
                                                (String) document.get("password"),
                                                (String) document.get("mail"),
                                                (long) document.get("yearOfBirth"),
                                                gen,
                                                userTypeTemp,
                                                (String) document.get("key_pg"));
                                        if (curr_user.getUserType().name()==UserType.parliament.name()) {
                                            //if user is parliament member
                                            next = new Intent(Login.this, HomeParliament.class);
                                        } else {
                                            //else- user is citizen
                                            next = new Intent(Login.this, HomeCitizen.class);
                                        }
                                        startActivity(next);
                                    }
                                }
                            });
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "ההתחברות נכשלה",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static User getCurrUser(){
        return curr_user;
    }

    public static void setCurr_user(User temp){
        curr_user=temp;
    }

    @Override
    public void onBackPressed() {

    }

    public void onClickCitizenRegister(View view) {
        Intent intent1 = new Intent(this, SignupCitizen.class);
        startActivity(intent1);
    }

    public void onClickSignupPM(View view) {
        Intent intent2 = new Intent(this, SignupPM.class);
        startActivity(intent2);
    }
}

