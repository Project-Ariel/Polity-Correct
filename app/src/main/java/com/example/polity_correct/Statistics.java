package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    private TextView pg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("סטטיסטיקות");

        String choice = getIntent().getExtras().get("pg").toString();
        String proposition_title = getIntent().getExtras().get("proposition_title").toString();
        pg = (TextView) findViewById(R.id.pg);
        pg.setText(choice);
        int[] res = (int[]) getIntent().getSerializableExtra("result");
        float sum = res[0] + res[1] + res[2];

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> votes = new ArrayList<>();
        votes.add(new PieEntry((res[0] / sum) * 100, "נגד"));
        votes.add(new PieEntry((res[1] / sum) * 100, "נמנע"));
        votes.add(new PieEntry((res[2] / sum) * 100, "בעד"));

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
    }
}