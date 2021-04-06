package com.example.crowdtest;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BinomialGraphManager extends GraphManager {

    private TreeMap<String, Integer[]> plotValues;

    ArrayList<BarEntry> successEntries;
    ArrayList<BarEntry> failureEntries;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public BinomialGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<String, Integer[]>(Comparator.reverseOrder());

        successEntries = new ArrayList<>();
        failureEntries = new ArrayList<>();

        label = "Count of Successes/Failures";


    }

    @Override
    protected void setPlotValues(Experiment experiment) {

        ArrayList<BinomialTrial> trials = ((Binomial) experiment).getTrials();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i=0; i < trials.size(); i++) {

            BinomialTrial trial = trials.get(i);

            Integer[] list;

            String dateStr = sdf.format(trial.getTimestamp());

            if (plotValues.containsKey(dateStr)){

                list = plotValues.get(dateStr);

            } else {

                list = new Integer[]{0,0};
            }

            if (trial.isSuccess()) {

                list[0] = list[0] + 1;

            } else {

                list[1] = list[1] + 1;
            }

            plotValues.put(dateStr, list);

        }

    }

    @Override
    protected void setUpGraphData() {

        for (Map.Entry<String, Integer[]> entry : plotValues.entrySet()) {

            Integer[] list = entry.getValue();
            successEntries.add(new BarEntry(successEntries.size(), list[0]));
            failureEntries.add(new BarEntry(successEntries.size(), list[1]));
            xAxisValues.add(entry.getKey());

        }

    }

    @Override
    protected BarData getGraphData(Experiment experiment) {

        setPlotValues(experiment);

        setUpGraphData();

        BarDataSet successDataSet = new BarDataSet(successEntries, "Success");
        successDataSet.setColor(Color.BLUE);
        BarDataSet failureDataSet = new BarDataSet(failureEntries, "Failure");
        failureDataSet.setColor(Color.RED);

        BarData theData = new BarData(successDataSet, failureDataSet);

        return theData;
    }

    @Override
    public void createGraph(BarChart barChart) {

        if (((Binomial) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData(experiment);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(3);

        xAxis.setCenterAxisLabels(true);

        theData.setBarWidth(0.16f);

        xAxis.setAxisMinimum(0);

        xAxis.setAxisMaximum(0 + barChart.getBarData().getGroupWidth(0.58f, 0.05f)*3);

        barChart.groupBars(0, 0.58f,0.05f);

    }
}
