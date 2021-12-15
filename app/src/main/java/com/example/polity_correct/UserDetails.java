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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserDetails extends AppCompatActivity {
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapter_items;
    FirebaseFirestore db;
    // We need to get this list from the DB
    String[] politicalGroups = {"ימינה", "כחול לבן", "העבודה", "ישראל ביתנו", "תקווה חדשה", "מרצ", "הציונות הדתית", "הרשימה המשותפת", "רעמ", "יהדות התורה", "שס", "יש עתיד", "הליכוד"};
    TextView name;
    TextView id;
    TextView date;
    User user;
    Intent next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        db = FirebaseFirestore.getInstance();

        user = getIntent().getExtras().getParcelable("user_obj");

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
                user.setKey_pg(items);
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
                    user.setGander(1);
                    break;
                }
            case R.id.female:
                if (checked) {
                    user.setGander(0);
                    break;
                }
        }
    }

    public void onClickOK(View view) {

        user.setUserName(name.getText().toString());
        user.setYearOfBirth(Integer.valueOf(date.getText().toString()));
        user.setUserType(UserType.citizen);

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").document(userId).set(user);

        next = new Intent(UserDetails.this, HomeCitizen.class);
        next.putExtra("curr_user",user);
        startActivity(next);

    }
}