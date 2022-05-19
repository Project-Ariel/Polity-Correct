package com.example.polity_correct;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static ArrayList<PoliticalGroup> political_groups = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private Spinner dropdown;
    private EditText name, date, mail;
    private EditText pass;
    private RadioButton gender;
    private User currUser= Login.getCurrUser();
    private String key_pg = currUser.getKey_pg();
    private String passwordInput;
    private int pg_select = 0;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("הגדרות");

        mail = (EditText) findViewById(R.id.text_usermail_citizen);
        mail.setText(currUser.getMail());

        name = (EditText) findViewById(R.id.User_full_name);
        name.setText(currUser.getUserName());

        date = (EditText) findViewById(R.id.User_year_of_birth);
        date.setText(""+(int) currUser.getYearOfBirth());

        pass = (EditText) findViewById(R.id.new_password_settings);
        pass.setText(currUser.getPassword());
        pass = (EditText) findViewById(R.id.new_password_valid_settings);
        pass.setText(currUser.getPassword());

        if (currUser.getGender() == 1) {
            gender = (RadioButton) findViewById(R.id.male);
            findViewById(R.id.female).setEnabled(false);
        } else {
            gender = (RadioButton) findViewById(R.id.female);
            findViewById(R.id.male).setEnabled(false);
        }
        gender.setChecked(true);
        gender.setEnabled(false);

        dropdown = (Spinner) findViewById(R.id.choosePG);

        DB.getPg(political_groups).addOnCompleteListener(task -> {
            int index = 0;
            for (PoliticalGroup p : political_groups) {
                if (currUser.getKey_pg().equals(p.getGroup_key())) {
                    pg_select = index;
                }
                titles.add(p.getGroup_name());
                index++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, titles);
            dropdown.setAdapter(adapter);
            dropdown.setSelection(pg_select);
            dropdown.setOnItemSelectedListener(this);

            if (currUser.getUserType() == UserType.parliament) {
                dropdown.setEnabled(false);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    if(Login.getCurrUser().getUserType().name().equals(UserType.parliament.name())) {
                        startActivity(new Intent(this, HomeParliament.class));
                    }
                    else
                        startActivity(new Intent(this, HomeCitizen.class));
                    break;
                case R.id.LogOut:
                    startActivity(new Intent(this, Login.class));
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //pg
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        key_pg = political_groups.get(position).getGroup_key();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onClickOK(View view) {
        if (validatePassword()) {
            updateDB();
            if (currUser.getUserType() == UserType.parliament) {
                startActivity(new Intent(this, HomeParliament.class));
            } else {
                startActivity(new Intent(this, HomeCitizen.class));
            }
        }
    }

    // TODO: 1/2/2022 Auth
    private void updateDB() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Update password
        if (!currUser.getPassword().equals(passwordInput)) {
            user.updatePassword(passwordInput); //Auth
            currUser.setPassword(passwordInput); //currUser
        }

        //Update key_pg
        if (!currUser.getKey_pg().equals(key_pg)) {
            currUser.setKey_pg(key_pg);//currUser
        }

        ////Update user in firestore
        DB.setCurrUser(currUser);
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
