package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SignupCitizen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ArrayList<PoliticalGroup> political_groups = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private Spinner dropdown;
    private EditText name, date;

    private User curr_user;
    private String mail, pass, key_pg;
    private int gen = -1;
    private boolean flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_citizen);

        dropdown = (Spinner) findViewById(R.id.choosePG);

        getDB().addOnCompleteListener(task -> {
            Intent i = getIntent();
            if (i != null) {
                for (PoliticalGroup p : political_groups) {
                    titles.add(p.getGroup_name());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(this);
        });
    }

    //pg
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        key_pg = political_groups.get(position).getGroup_key();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    //get Political Groups from DB
    private Task<QuerySnapshot> getDB() {
        return FirebaseFirestore.getInstance().collection("PoliticalGroups")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PoliticalGroup p = new PoliticalGroup(document.getId(), (String) document.get("group_name"), (String) document.get("abbreviation"), (String) document.get("group_website"));
                            political_groups.add(p);
                        }
                    }
                });
    }

    //gender
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    gen = 1;
                    break;
                }
            case R.id.female:
                if (checked) {
                    gen = 0;
                    break;
                }
        }
    }

    public void onClickOK(View view) {
        if (validateEmail() && validatePassword()) {
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            name = ((EditText) findViewById(R.id.User_full_name));
                            date = ((EditText) findViewById(R.id.User_year_of_birth));
                            curr_user = new Citizen(name.getText().toString(), pass, mail,
                                    Long.valueOf(date.getText().toString()), gen, UserType.citizen, key_pg);
                            String userId = mAuth.getCurrentUser().getUid();
                            db.collection("Users").document(userId).set(curr_user);
                            Login.setCurr_user(curr_user);
                            startActivity(new Intent(SignupCitizen.this, HomeCitizen.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupCitizen.this, "Signup failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateEmail() {
        mail = ((TextView) findViewById(R.id.textUsermailCitizen)).getText().toString();
        if (mail.isEmpty()) {
            Toast.makeText(this, "email can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                flag = true;
                mAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();
                            if (!signInMethods.isEmpty()) {
                                Toast.makeText(SignupCitizen.this, "This email is already exist.", Toast.LENGTH_SHORT).show();
                                flag = false;
                            }
                        }
                    }
                });
                return flag;
            }
        }
    }

    private boolean validatePassword() {
        pass = ((TextView) findViewById(R.id.new_password)).getText().toString();
        String passwordInput = ((TextView) findViewById(R.id.new_password)).getText().toString();
        String ConfirmPasswordInput = ((TextView) findViewById(R.id.new_password_valid)).getText().toString();
        if (passwordInput.isEmpty()) {
            Toast.makeText(this, "Password can't be empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordInput.length() < 6) {
            Toast.makeText(this, "Password must be at least 5 characters",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordInput.equals(ConfirmPasswordInput)) {
            Toast.makeText(this, "Password Would Not be matched",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "Password Matched",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}