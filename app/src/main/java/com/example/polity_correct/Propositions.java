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

import java.util.ArrayList;

public class Propositions extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Proposition> propositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("מה חדש?");

        Intent in = getIntent();
        propositions= (ArrayList<Proposition>) in.getSerializableExtra("propositions");
        for (Proposition i : propositions) {
            titles.add(i.getTitle());
        }

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new listAdapter(this, R.layout.item_view, R.id.itemTextView, titles);
        listView.setAdapter(arrayAdapter);
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
}