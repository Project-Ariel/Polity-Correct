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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserDetails extends AppCompatActivity {
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapter_items;
    // We need to get this list from the DB
    String[] politicalGroups = {"ימינה", "כחול לבן", "העבודה", "ישראל ביתנו", "תקווה חדשה", "מרצ", "הציונות הדתית", "הרשימה המשותפת", "רעמ", "יהדות התורה", "שס", "יש עתיד", "הליכוד"};
    TextView name;
    TextView id;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        name = ((TextView) findViewById(R.id.User_full_name));
        id = ((TextView) findViewById(R.id.User_ID));
        date = ((TextView) findViewById(R.id.User_year_of_birth));

        autoCompleteText = findViewById(R.id.autoCompleteTextView);
        //String PG[]= getResources().getStringArray(R.array.מפלגות);
        adapter_items = new ArrayAdapter<String>(this, R.layout.drop_down_item_political_group, politicalGroups);
        autoCompleteText.setAdapter(adapter_items);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void onClickOK(View view) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+FirebaseAuth.getInstance().getUid());
        User user = new Citizen(
                id.getText().toString(), name.getText().toString(), "pass", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(),Integer.getInteger(date.getText().toString()), UserType.citizen, 0);
        myRef.setValue(user);

        Intent next;
        Bundle b = new Bundle();
        b.putString("User name", name.getText().toString());
        next = new Intent(UserDetails.this, HomeCitizen.class);
        next.putExtras(b);
        startActivity(next);
    }
}