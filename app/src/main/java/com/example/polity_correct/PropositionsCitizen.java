package com.example.polity_correct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PropositionsCitizen extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Proposition> propositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions_citizen);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("מה חדש?");

        getNotVotedPropositionsFromDB().addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                titles.add(p.getTitle());
            }

            listView = (ListView) findViewById(R.id.listViewC);
            ArrayAdapter<String> arrayAdapter = new listAdapter(this, R.layout.item_view_vote, R.id.itemTextView, titles);
            listView.setAdapter(arrayAdapter);
        });
    }


    class listAdapter extends ArrayAdapter<String> {
        public listAdapter(@NonNull Context context, int item_view, int itemTextView, ArrayList<String> list) {
            super(context, item_view, itemTextView, list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final View view = super.getView(position, convertView, parent);
            final Button btn = (Button) view.findViewById(R.id.button_vote);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openVotePage(v, position);
                }
            });
            return view;
        }
    }

    public void openVotePage(View view, int pos) {
        Intent next = new Intent(this, Vote.class);
        next.putExtra("current proposition", propositions.get(pos));
        startActivity(next);
    }

    private Task<QuerySnapshot> getNotVotedPropositionsFromDB() {
        propositions = new ArrayList<>();
        return FirebaseFirestore.getInstance().collection("Propositions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Proposition p = new Proposition(document.getId(), (String) document.get("title"), (String) document.get("status"), (String) document.get("description"), (String) document.get("category"), (boolean) document.get("voted"));
                            if (!p.wasVoted()) {
                                propositions.add(p);
                            }
                        }
                    }
                });
    }
}