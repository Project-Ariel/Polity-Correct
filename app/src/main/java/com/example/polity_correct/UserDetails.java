package com.example.polity_correct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapter_items;
    FirebaseFirestore db;
    ArrayList<PoliticalGroup> political_groups = new ArrayList<>();


    private Spinner dropdown;
    private ArrayList<String> titles = new ArrayList<>();
    private TextView name;
    private TextView id;
    private TextView date;
    private User curr_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        db = FirebaseFirestore.getInstance();

        curr_user = Login.getCurrUser();

        name = ((TextView) findViewById(R.id.User_full_name));
        id = ((TextView) findViewById(R.id.User_ID));
        date = ((TextView) findViewById(R.id.User_year_of_birth));

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


    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("here");
    }


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        curr_user.setKey_pg(political_groups.get(position).getGroup_key());
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    curr_user.setGander(1);
                    break;
                }
            case R.id.female:
                if (checked) {
                    curr_user.setGander(0);
                    break;
                }
        }
    }

    public void onClickOK(View view) {

        curr_user.setUserName(name.getText().toString());
        curr_user.setYearOfBirth(Long.valueOf(date.getText().toString()));
        curr_user.setUserType(UserType.citizen);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").document(userId).set(curr_user);
//        Login.setCurr_user(curr_user);

        startActivity(new Intent(this, HomeCitizen.class));
    }
}