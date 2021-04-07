package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class NonNegativeGraphManager extends GraphManager{

    private TreeMap<String, Integer> plotValues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NonNegativeGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<String, Integer>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {

                Integer i1 = Integer.parseInt(s);
                Integer i2 = Integer.parseInt(t1);
                return i1.compareTo(i2);
            }
        });

        label = "# Trials with Given Value";

    }

    @Override
    protected void setPlotValues(Experiment experiment) {

        ArrayList<NonNegativeTrial> trials = ((NonNegative) experiment).getTrials();

        for (int i=0; i < trials.size(); i++) {

            NonNegativeTrial trial = trials.get(i);

            String key = String.valueOf(trial.getCount());

            if (plotValues.containsKey(key)){

                Integer oldValue = plotValues.get(key);

                plotValues.put(key, oldValue + 1);

            } else {

                plotValues.put(key, 1);

            }

        }

    }

    @Override
    protected void setUpGraphData() {

        for (Map.Entry<String, Integer> entry : plotValues.entrySet()) {

            barEntries.add(new BarEntry(barEntries.size(), entry.getValue()));
            xAxisValues.add("Count " + entry.getKey());

        }

    }

    @Override
    public void createGraph(BarChart barChart) {

        if (((NonNegative) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData(experiment);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(5);

        return;

    }
}
