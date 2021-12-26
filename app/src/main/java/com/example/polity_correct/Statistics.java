package com.example.polity_correct;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics extends AppCompatActivity {

    private TextView pg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        TextView title = (TextView) findViewById(R.id.title_page);
        title.setText("סטטיסטיקות");

        String choice= getIntent().getExtras().get("pg").toString();
        String proposition_title= getIntent().getExtras().get("proposition_title").toString();
        pg= (TextView) findViewById(R.id.pg) ;
        pg.setText(choice);
        HashMap<Integer, Integer> res = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("result");
        System.out.println("res:"+res);
        int sum= res.get(0)+res.get(1)+res.get(2);

        PieChart pieChart =findViewById(R.id.pieChart);

        ArrayList<PieEntry> votes= new ArrayList<>();
        votes.add(new PieEntry(res.get(0)/sum, "נגד"));
        votes.add(new PieEntry(res.get(1)/sum, "נמנע"));
        votes.add(new PieEntry(res.get(2)/sum, "בעד"));

        PieDataSet pieDataSet = new PieDataSet(votes, proposition_title);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(proposition_title);
        pieChart.animate();
    }
}