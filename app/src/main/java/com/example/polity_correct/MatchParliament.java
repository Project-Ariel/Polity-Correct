package com.example.polity_correct;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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
                    matching.setVisibility(View.VISIBLE);
                    matching_img.setVisibility(View.VISIBLE);
                    parliament_text.setText("parliament");
                    citizen_text.setText(Login.getCurrUser().getUserName());
                    percent_text.setText("100%");
                    showBarChart();
                }
            }
        }, 30);
    }

    public void showBarChart() {
        barChart = (BarChart) findViewById(R.id.barchart);
        barChart.setVisibility(View.VISIBLE);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 20, "לפיד"));
        entries.add(new BarEntry(1f, 60, "בנט"));
        entries.add(new BarEntry(2f, 40, "ליצמן"));
        entries.add(new BarEntry(3f, 80, "סמוטריץ'"));
        entries.add(new BarEntry(4f, 100, "ביבי"));

        String[] PARLIAMENT= new String[5];
        for(BarEntry e:entries){
            PARLIAMENT[(int)e.getX()]=(String) e.getData();
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




