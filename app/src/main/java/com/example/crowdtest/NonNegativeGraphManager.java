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

/**
 * Class for creating a histogram for a NonNegative experiment
 */
public class NonNegativeGraphManager extends GraphManager{

    private TreeMap<String, Integer> plotValues;

    /**
     * Constructor for NegativeGraphManager
     * @param experiment
     *     Experiment with trial data to be displayed in a histogram
     */
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

    /**
     * Aggregates data to be displayed in histogram using TreeMap plotValues object
     * Keys of plotValues will be set up as a count value (ex. count entered is 2, is 3, etc)
     * Values are the number of trials with that count value
     */
    @Override
    protected void setPlotValues() {

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

    /**
     * Initializes array of that will contain ordered BarEntry objects
     * Initializes array that wil contain the x axis values
     */
    @Override
    protected void setUpGraphData() {

        for (Map.Entry<String, Integer> entry : plotValues.entrySet()) {

            barEntries.add(new BarEntry(barEntries.size(), entry.getValue()));
            xAxisValues.add("Count " + entry.getKey());

        }

    }

    /**
     * Fully set up the bar chart to be displayed
     * @param barChart
     *     Unformatted BarChart object
     */
    @Override
    public void createGraph(BarChart barChart) {

        //if the experiment doesn't have any trials, leave graph empty
        if (((NonNegative) experiment).getTrials().size() == 0) {

            return;
        }

        //get data for the graph
        BarData theData = getGraphData();

        //set up the x axis
        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        //set up the data for the graph
        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(5);

        return;

    }
}
