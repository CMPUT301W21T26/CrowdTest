package com.example.crowdtest.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.crowdtest.BinomialGraphManager;
import com.example.crowdtest.BinomialPlotManager;
import com.example.crowdtest.CountGraphManager;
import com.example.crowdtest.CountPlotManager;
import com.example.crowdtest.MeasurementGraphManager;
import com.example.crowdtest.MeasurementPlotManager;
import com.example.crowdtest.NonNegativeGraphManager;
import com.example.crowdtest.NonNegativePlotManager;
import com.example.crowdtest.R;
import com.example.crowdtest.StatisticsStringCreator;
import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.NonNegative;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.google.android.gms.maps.MapView;

import java.text.DecimalFormat;
import java.util.Hashtable;

public class ExpStatisticsActivity extends AppCompatActivity {

    TextView statsText;

    BarChart barChart;

    LineChart lineChart;

    Experiment experiment;

    TextView histogramTitle;

    TextView plotTitle;

    StatisticsStringCreator statisticsStringCreator;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_statistics);

        statsText = findViewById(R.id.statistics_text);

        Intent intent = getIntent();

        experiment = (Experiment) intent.getSerializableExtra("EXP");

        DecimalFormat f = new DecimalFormat("##.00");

        barChart = (BarChart) findViewById(R.id.bar_chart);

        lineChart = (LineChart) findViewById(R.id.line_chart);

        createBarChart();

        createPlot();

        statisticsStringCreator = new StatisticsStringCreator(experiment);

        String statisticsString = statisticsStringCreator.createStatisticsString();

        statsText.setText(statisticsString);

        MapView map = findViewById(R.id.participants_mapView);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createBarChart() {

        formatBarChart();

        histogramTitle = findViewById(R.id.bar_chart_title_text);

        if (experiment instanceof Measurement) {


            histogramTitle.setText("Number of Measurements by Value Range");

            MeasurementGraphManager measurementGraphManager = new MeasurementGraphManager(experiment);

            measurementGraphManager.createGraph(barChart);


        } else if (experiment instanceof Count) {

            histogramTitle.setText("Counts to Date");

            CountGraphManager countGraphManager = new CountGraphManager(experiment);

            countGraphManager.createGraph(barChart);

        } else if (experiment instanceof Binomial) {

            histogramTitle.setText("Successes and Failures by Date");

            BinomialGraphManager binomialGraphManager = new BinomialGraphManager(experiment);

            binomialGraphManager.createGraph(barChart);

        }
        else if (experiment instanceof NonNegative){

            histogramTitle.setText("Num. of Trials by Non-Negative Count Value");

            NonNegativeGraphManager nonNegativeGraphManager = new NonNegativeGraphManager(experiment);

            nonNegativeGraphManager.createGraph(barChart);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPlot() {

        formatLineChart();

        plotTitle = findViewById(R.id.line_chart_title_text);

        if (experiment instanceof Count) {

            plotTitle.setText("Total Counts Over Time");

            CountPlotManager countPlotManager = new CountPlotManager(experiment);

            countPlotManager.createPlot(lineChart);


        } else if (experiment instanceof Binomial){

            plotTitle.setText("Success Rate Over Time ");

            BinomialPlotManager binomialPlotManager = new BinomialPlotManager(experiment);

            binomialPlotManager.createPlot(lineChart);

        } else if (experiment instanceof Measurement) {

            plotTitle.setText("Average Measurement Over Time");

            MeasurementPlotManager measurementPlotManager = new MeasurementPlotManager(experiment);

            measurementPlotManager.createPlot(lineChart);


        } else if (experiment instanceof NonNegative) {

            plotTitle.setText("Average NonNegative Count Total Over Time");

            NonNegativePlotManager nonNegativePlotManager = new NonNegativePlotManager(experiment);

            nonNegativePlotManager.createPlot(lineChart);

        }

    }

    private void formatLineChart(){

        lineChart.setNoDataText("No Trials Recorded Yet");
        lineChart.getDescription().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMax(0.25f);
        xAxis.setSpaceMin(0.25f);
        xAxis.setGranularityEnabled(true);
    }

    private void formatBarChart(){

        barChart.setNoDataText("No Trials Recorded Yet");
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
    }

    private void mapInit(){

    }
}