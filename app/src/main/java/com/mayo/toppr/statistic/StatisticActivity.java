package com.mayo.toppr.statistic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mayo.toppr.R;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4f, "Jan", 0));
        entries.add(new PieEntry(8f, "Feb", 1));
        entries.add(new PieEntry(6f, "Mar,", 2));
        entries.add(new PieEntry(12f, "Apr", 3));
        entries.add(new PieEntry(18f, "May", 4));
        entries.add(new PieEntry(9f, "Jun", 5));

        //x-axis
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


        PieData pieData = new PieData(dataset);
        pieData.setValueTextSize(20.0f);//textsize of values


        PieChart pieChart = (PieChart) findViewById(R.id.barchart);
        pieChart.setData(pieData);
        pieChart.setDescription("Calls");
        pieChart.setRotationEnabled(true);
        pieChart.setEntryLabelTextSize(20.0f);//textsize of lables
        pieChart.setCenterText("Calls");

        setToolbar();
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Statistics");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //reduces the gap between navigation icon and the title in the toolbar
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
