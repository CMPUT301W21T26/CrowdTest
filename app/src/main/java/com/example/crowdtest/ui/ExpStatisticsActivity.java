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

import java.text.DecimalFormat;
import java.util.Hashtable;

public class ExpStatisticsActivity extends AppCompatActivity {

    TextView statsText;

    BarChart barChart;

    LineChart lineChart;

    Experiment experiment;

    TextView histogramTitle;

    TextView plotTitle;


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

        barChart.setNoDataText("No Trials Recorded Yet");

        lineChart = (LineChart) findViewById(R.id.line_chart);

        lineChart.setNoDataText("No Trials Recorded Yet");

        createBarChart(experiment);

        createPlot(experiment);

        String statisticsString = (new StatisticsStringCreator(experiment)).createStatisticsString();

        statsText.setText(statisticsString);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createBarChart(Experiment experiment) {

        XAxis xAxis = barChart.getXAxis();

        formatBarChart();

        BarData theData;

        histogramTitle = findViewById(R.id.bar_chart_title_text);

        if (experiment instanceof Measurement) {


            histogramTitle.setText("Number of Measurements by Value Range");

            if (((Measurement) experiment).getTrials().size() == 0) {

                return;
            }

            MeasurementGraphManager measurementGraphManager = new MeasurementGraphManager();

            theData = measurementGraphManager.getGraphData(experiment);

            xAxis.setValueFormatter(measurementGraphManager.getXAxis());

            barChart.setData(theData);

            barChart.setVisibleXRangeMaximum(5);

        } else if (experiment instanceof Count) {

            histogramTitle.setText("Counts to Date");

            if (((Count) experiment).getTrials().size() == 0) {

                return;
            }

            CountGraphManager countGraphManager = new CountGraphManager();

            theData = countGraphManager.getGraphData(experiment);

            xAxis.setValueFormatter(countGraphManager.getXAxis());

            barChart.setData(theData);

            barChart.setVisibleXRangeMaximum(5);

        } else if (experiment instanceof Binomial) {

            histogramTitle.setText("Successes and Failures by Date");

            if (((Binomial) experiment).getTrials().size() == 0) {

                return;
            }

            BinomialGraphManager binomialGraphManager = new BinomialGraphManager();

            theData = binomialGraphManager.getGraphData(experiment);
            
            xAxis.setValueFormatter(binomialGraphManager.getXAxis());

            barChart.setData(theData);

            barChart.setVisibleXRangeMaximum(3);

            xAxis.setCenterAxisLabels(true);

            theData.setBarWidth(0.16f);

            xAxis.setAxisMinimum(0);

            Integer entries = ((Binomial) experiment).getTrials().size();

            xAxis.setAxisMaximum(0 + barChart.getBarData().getGroupWidth(0.58f, 0.05f)*3);

            barChart.groupBars(0, 0.58f,0.05f);

        }
        else if (experiment instanceof NonNegative){

            histogramTitle.setText("Num. of Trials by Non-Negative Count Value");

            if (((NonNegative) experiment).getTrials().size() == 0) {

                return;
            }

            NonNegativeGraphManager nonNegativeGraphManager = new NonNegativeGraphManager();

            theData = nonNegativeGraphManager.getGraphData(experiment);

            xAxis.setValueFormatter(nonNegativeGraphManager.getXAxis());

            barChart.setData(theData);

            barChart.setVisibleXRangeMaximum(5);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPlot(Experiment experiment) {

        LineData theData;

        XAxis xAxis = lineChart.getXAxis();

        formatLineChart();

        plotTitle = findViewById(R.id.line_chart_title_text);

        if (experiment instanceof Count) {

            plotTitle.setText("Total Counts Over Time");

            if (((Count) experiment).getTrials().size() == 0) {

                return;
            }

            CountPlotManager countPlotManager = new CountPlotManager(experiment);

            theData = countPlotManager.getGraphData();

            xAxis.setValueFormatter(countPlotManager.getXAxis());


        } else if (experiment instanceof Binomial){

            plotTitle.setText("Success Rate Over Time ");

            if (((Binomial) experiment).getTrials().size() == 0) {

                return;
            }

            BinomialPlotManager binomialPlotManager = new BinomialPlotManager(experiment);

            theData = binomialPlotManager.getGraphData();

            xAxis.setValueFormatter(binomialPlotManager.getXAxis());


        } else if (experiment instanceof Measurement) {

            plotTitle.setText("Average Measurement Over Time");

            if (((Measurement) experiment).getTrials().size() == 0) {

                return;
            }

            MeasurementPlotManager measurementPlotManager = new MeasurementPlotManager(experiment);

            theData = measurementPlotManager.getGraphData();

            xAxis.setValueFormatter(measurementPlotManager.getXAxis());


        } else if (experiment instanceof NonNegative) {

            plotTitle.setText("Average NonNegative Count Total Over Time");

            if (((NonNegative) experiment).getTrials().size() == 0) {

                return;
            }

            NonNegativePlotManager nonNegativePlotManager = new NonNegativePlotManager(experiment);

            theData = nonNegativePlotManager.getGraphData();

            xAxis.setValueFormatter(nonNegativePlotManager.getXAxis());

        } else {

            theData = null;
        }

        lineChart.setData(theData);


    }

    private void formatLineChart(){

        lineChart.getDescription().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMax(0.25f);
        xAxis.setSpaceMin(0.25f);
        xAxis.setGranularityEnabled(true);
    }

    private void formatBarChart(){


        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
    }

}