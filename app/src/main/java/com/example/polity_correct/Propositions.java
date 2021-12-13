package com.example.polity_correct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Propositions extends AppCompatActivity {

    private ListView listView;


    // array objects
    String list[] = {"רעות", "חוק", "Database", "Python",
            "Java", "Operating System", "Compiler Design", "Android Development", "C-Programming", "Data Structure", "Database", "Python",
            "Java", "Operating System", "Compiler Design", "Android Development", "C-Programming", "Data Structure", "Database", "Python",
            "Java", "Operating System", "Compiler Design", "Android Development", "C-Programming", "Data Structure", "Database", "Python",
            "Java", "Operating System", "Compiler Design", "Android Development", "C-Programming", "Data Structure", "Database", "Python",
            "Java", "Operating System", "Compiler Design", "Android Development", "reut"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions);

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new listAdapter(this, R.layout.item_view, R.id.itemTextView, list);
        listView.setAdapter(arrayAdapter);


    }


    class listAdapter extends ArrayAdapter<String> {
        public listAdapter(@NonNull Context context, int item_view, int itemTextView, String[] list) {
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
                    openVotePage(v,position);
                }
            });
            return view;
        }
    }
    public void openVotePage(View view,int pos) {
//        simpleListView.getAdapter().getView(R.id.button_vote, );

        Intent next = new Intent(this, Vote.class);
        Bundle b = new Bundle();
        b.putString("proposition_key", ""+pos);
        next.putExtras(b);
        startActivity(next);
    }
}