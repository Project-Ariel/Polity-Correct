package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CitizenDetails extends AppCompatActivity {
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapter_items;
    FirebaseFirestore db;
    // We need to get this list from the DB
    String[] politicalGroups = {"ימינה", "כחול לבן", "העבודה", "ישראל ביתנו", "תקווה חדשה", "מרצ", "הציונות הדתית", "הרשימה המשותפת", "רע\"ם", "יהדות התורה", "ש\"ס", "יש עתיד", "הליכוד"};
    TextView name;
    TextView id;
    TextView date;
    User curr_user;
    Intent next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_details);

        db = FirebaseFirestore.getInstance();

        curr_user = getIntent().getExtras().getParcelable("user_obj");

        name = ((TextView) findViewById(R.id.User_full_name));
        id = ((TextView) findViewById(R.id.User_ID));
        date = ((TextView) findViewById(R.id.User_year_of_birth));

        autoCompleteText = findViewById(R.id.autoCompleteTextView);
        adapter_items = new ArrayAdapter<String>(this, R.layout.drop_down_item_political_group, politicalGroups);
        autoCompleteText.setAdapter(adapter_items);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items, Toast.LENGTH_SHORT).show();
                curr_user.setKey_pg(items);
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

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").document(userId).set(curr_user);
        Login.setCurr_user(curr_user);

        next = new Intent(CitizenDetails.this, HomeCitizen.class);
        next.putExtra("curr_user", curr_user);
        startActivity(next);

    }
}