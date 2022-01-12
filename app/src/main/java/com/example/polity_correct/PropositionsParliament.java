package com.example.polity_correct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PropositionsParliament extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> titles;
    private static ArrayList<Proposition> propositions;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions_parliament);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("מה חדש?");

        propositions = new ArrayList<>();
        titles = new ArrayList<>();

        DB.getPropositionsNotVoted(propositions).addOnCompleteListener(task -> {
            for (Proposition p : propositions) {
                    titles.add(p.getTitle());
            }

            listView = (ListView) findViewById(R.id.listViewP);
            ArrayAdapter<String> arrayAdapter = new listAdapter(this, R.layout.item_view_statistic, R.id.itemTextView, titles);
            listView.setAdapter(arrayAdapter);
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    startActivity(new Intent(this, HomeParliament.class));
                    break;
                case R.id.Statistics:
                    Intent intent = new Intent(this, ChooseResultUsers.class);
                    intent.putExtra("index_current_proposition", 0);
                    startActivity(intent);
                    break;
                case R.id.Propositions:
                    startActivity(new Intent(this, PropositionsParliament.class));
                    break;
                case R.id.UpdatePassword:
                    startActivity(new Intent(this, Settings.class));
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
                    openStatisticsPage(v, position);
                }
            });
            return view;
        }
    }

    public void openStatisticsPage(View view, int pos) {
        Intent next = new Intent(this, ChooseResultUsers.class);
        next.putExtra("index_current_proposition", pos);
        next.putExtra("propositions", propositions);
        startActivity(next);
    }
}