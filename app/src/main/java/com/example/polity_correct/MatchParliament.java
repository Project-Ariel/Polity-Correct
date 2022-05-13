package com.example.polity_correct;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MatchParliament extends AppCompatActivity {

    int i = 0;
    private ProgressBar circularProgressBar;
    private TextView loading_text;
    private TextView parliament_text;
    private TextView citizen_text;
    private TextView percent_text;
    private ImageView matching_img;
    private TextView matching;
    private BarChart barChart;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    static int[] votes_user;
    static double[] rating;
    static HashMap<String, int[]> votes_pm;
    private LinkedHashMap<String, Integer> percents_result;
//    private String[] best_parliaments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_parliament);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("התאמת חבר כנסת");

        circularProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        loading_text = (TextView) findViewById(R.id.loading);
        parliament_text = (TextView) findViewById(R.id.parliament_name);
        citizen_text = (TextView) findViewById(R.id.citizen_name);
        percent_text = (TextView) findViewById(R.id.matching_percent);
        matching_img = (ImageView) findViewById(R.id.matching_img);
        matching = (TextView) findViewById(R.id.matching_text);

        // Handler for circular progress bar timer
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i <= 100) {
                    circularProgressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 30);
                } else {
                    handler.removeCallbacks(this);
                    circularProgressBar.setVisibility(View.GONE);
                    loading_text.setVisibility(View.GONE);

                    show_result();

                }
            }
        }, 30);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav = (NavigationView) findViewById(R.id.navView);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
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

    private void show_result() {
//        DB.setVotesForAlgo(votes_user, rating, votes_pm).addOnCompleteListener(task -> {

        // manual just for test
        votes_user = new int[]{0, 1, 0, 1, 2};
        rating = new double[]{3, 4, 2, 5, 1};
        votes_pm = new HashMap<>();
        votes_pm.put("0WyASJFHH7eou7dLZnAnsuWnyK12", new int[]{0, 2, 1, 1, 1});
        votes_pm.put("1HvhnuVmswM5nU1TJZz85SOeOdo2", new int[]{1, 2, 1, 2, 1});
        votes_pm.put("1MOrTMUPcscNxpoyw95ajl7HNqE3", new int[]{0, 0, 0, 0, 0});
        votes_pm.put("2Wou736KshQC8Hh6UbaSS3zeSip1", new int[]{1, 1, 1, 1, 1});
        votes_pm.put("2jRqVEJIkQcJIqFMb7MNwANauz92", new int[]{0, 2, 0, 1, 2});
        votes_pm.put("2xC4ldbBpQPn2XAnqXav5hMv0zD3", new int[]{2, 2, 2, 2, 2});
        //

        boolean flag = true;
        for (int i = 0; i < votes_user.length; i++) {
            if (votes_user[i] == -1) {
                flag = false;
            }
        }
        if (flag) {
            percents_result = Algo.matchParliament(votes_user, rating, votes_pm);
            String first_pm = percents_result.keySet().iterator().next();
            Integer percent_best_pm = percents_result.get(first_pm);
            User best_pm = new User(first_pm);
            DB.getUser(best_pm).addOnCompleteListener(task0 -> {
                parliament_text.setText(best_pm.getUserName());
                percent_text.setText(percent_best_pm.toString()+"%");
                matching.setVisibility(View.VISIBLE);
                matching_img.setVisibility(View.VISIBLE);
                citizen_text.setText(Login.getCurrUser().getUserName());
                showBarChart();
            });
        } else {
            loading_text.setText("לצערנו אין מספיק הצבעות שלך על חוקים שהצובעו על ידי חברי כנסת..\n\nגש להצביע");
            loading_text.setVisibility(View.VISIBLE);
        }
//        });
    }

//    public void prepareDataBarChart() {
//        parliaments = new ArrayList<>();
//        for (Map.Entry<String, Double> it : percents_result.entrySet()) {
//            User pm = new User(it.getKey());
//            DB.getUser(pm).addOnCompleteListener(task -> {
//                parliaments.add(pm.getUserName());
//            });
//        }
//    }

    public void showBarChart() {
        barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Integer> it : percents_result.entrySet()) {
            if (count < 5) {
                entries.add(new BarEntry((float) count, it.getValue(), it.getKey()));
            }
            count++;
        }
        barChart.setVisibility(View.VISIBLE);


        String[] PARLIAMENT = new String[5];
        for (BarEntry e : entries) {
            PARLIAMENT[(int) e.getX()] = (String) e.getData();
        }
        BarDataSet bardataset = new BarDataSet(entries, "התפלגות ההתאמות");


        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return PARLIAMENT[(int) value];
            }
        });

        BarData data = new BarData(bardataset);
        data.setValueTextSize(12f);
        barChart.setData(data); // set the data and list of labels into chart
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        data.setDrawValues(true);

        xAxis.setDrawLabels(true);
        barChart.setFitBars(true);
        barChart.setDescription(null);
        barChart.animateY(3000);
    }
}




