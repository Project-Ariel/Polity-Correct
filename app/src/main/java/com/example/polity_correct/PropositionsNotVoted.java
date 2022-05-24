package com.example.polity_correct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PropositionsNotVoted extends Fragment {

    private ListView listView;
    private ArrayList<String> titles;
    private static ArrayList<Proposition> propositions;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view =inflater.inflate(R.layout.propositions_voted, container, false);
        TextView title = (TextView)view.findViewById(R.id.title_page);

        title.setText("מה חדש?");

        propositions = new ArrayList<>();
        titles = new ArrayList<>();

        DB.getPropositions(propositions, false).addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                titles.add(p.getTitle());
            }

            listView = (ListView) view.findViewById(R.id.listViewC);
            ArrayAdapter<String> arrayAdapter = new listAdapter(this.getActivity(), R.layout.item_view_vote, R.id.itemTextView, titles);
            listView.setAdapter(arrayAdapter);
        });

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this.getActivity(), drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        Intent next = new Intent(this.getActivity(), Vote.class);
        next.putExtra("current proposition", propositions.get(pos));
        startActivity(next);
    }
}