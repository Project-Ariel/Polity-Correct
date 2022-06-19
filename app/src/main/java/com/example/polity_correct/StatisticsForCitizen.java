package com.example.polity_correct;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class StatisticsForCitizen extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_for_citizen);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("סטטיסטיקות");

        String proposition_title = getIntent().getExtras().get("proposition_title").toString();
        double[] res = (double[]) getIntent().getSerializableExtra("result");

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> votes = new ArrayList<>();
        votes.add(new PieEntry((float) res[0], "נגד"));
        votes.add(new PieEntry((float) res[1], "נמנע"));
        votes.add(new PieEntry((float) res[2], "בעד"));

        PieDataSet pieDataSet = new PieDataSet(votes, proposition_title);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueFormatter(new PercentFormatter());

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(proposition_title);
        pieChart.animate();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    startActivity(new Intent(this, HomeCitizen.class));
                    break;
                case R.id.UpdateDetails:
                    startActivity(new Intent(this, Settings.class));
                    break;
                case R.id.Vote:
                    startActivity(new Intent(this, PropositionsCitizen.class));
                    break;
                case R.id.Results:
                    startActivity(new Intent(this, Results.class));
                    break;
                case R.id.Algo:
                    startActivity(new Intent(this, MatchParliament.class));
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

    public void OnClickLogo(View view) {
        startActivity(new Intent(this, HomeCitizen.class));
    }
}