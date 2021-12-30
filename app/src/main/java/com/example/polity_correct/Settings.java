package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<PoliticalGroup> political_groups = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private Spinner dropdown;
    private TextView name, date, mail;
    private EditText pass;
    private RadioButton gender;
    private String key_pg = Login.getCurrUser().getKey_pg();
    private String passwordInput;
    private int pg_select = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הגדרות");

        mail = (TextView) findViewById(R.id.textUsermailCitizen);
        mail.setText(Login.getCurrUser().getMail());

        name = (TextView) findViewById(R.id.User_full_name);
        name.setText(Login.getCurrUser().getUserName());

        date = (TextView) findViewById(R.id.User_year_of_birth);
        date.setText(Login.getCurrUser().getYearOfBirth().toString());

        pass= (EditText) findViewById(R.id.new_password_settings);
        pass.setText(Login.getCurrUser().getPassword());
        pass= (EditText) findViewById(R.id.new_password_valid_settings);
        pass.setText(Login.getCurrUser().getPassword());

        if (Login.getCurrUser().getGender() == 1) {
            gender = (RadioButton) findViewById(R.id.male);
        } else {
            gender = (RadioButton) findViewById(R.id.female);
        }
        gender.setChecked(true);
        gender.setEnabled(false);

        dropdown = (Spinner) findViewById(R.id.choosePG);

        getDB().addOnCompleteListener(task -> {
            Intent i = getIntent();
            if (i != null) {
                int index = 0;
                for (PoliticalGroup p : political_groups) {
                    if (Login.getCurrUser().getKey_pg().equals(p.getGroup_key())) {
                        pg_select = index;
                    }
                    titles.add(p.getGroup_name());
                    index++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setSelection(pg_select);
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

    public void onClickOK(View view) {
        // TODO: 12/30/2021 roi- update DB
        Login.getCurrUser().setKey_pg(key_pg);
        if(validatePassword()) {
            Login.getCurrUser().setPassword(passwordInput);
            startActivity(new Intent(this, HomeCitizen.class));
        }
    }

    private boolean validatePassword() {
        passwordInput = ((EditText) findViewById(R.id.new_password_settings)).getText().toString();
        String ConfirmPasswordInput = ((EditText) findViewById(R.id.new_password_valid_settings)).getText().toString();
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
            return true;
        }
    }
}
