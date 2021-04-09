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

/**
 * Class for creating a histogram for a Binomial experiment
 */
public class BinomialGraphManager extends GraphManager {

    private TreeMap<String, Integer[]> plotValues;

    ArrayList<BarEntry> successEntries;
    ArrayList<BarEntry> failureEntries;

    /**
     * Constructor for BinomialGraphManager
     * @param experiment
     *     Experiment with trial data to be displayed in a histogram
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BinomialGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<String, Integer[]>(Comparator.reverseOrder());

        successEntries = new ArrayList<>();
        failureEntries = new ArrayList<>();

        label = "Count of Successes/Failures";


    }

    /**
     * Aggregates data to be displayed in histogram using TreeMap plotValues object
     * Keys are dates of the experiment
     * Values are a array with the number of trials that were successes on that date
     * in the first index, and the number of failures on that date in the second index
     */
    @Override
    protected void setPlotValues() {

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

    /**
     * Initializes array of that will contain ordered BarEntry objects
     * Initializes array that wil contain the x axis values
     */
    @Override
    protected void setUpGraphData() {

        for (Map.Entry<String, Integer[]> entry : plotValues.entrySet()) {

            Integer[] list = entry.getValue();
            successEntries.add(new BarEntry(successEntries.size(), list[0]));
            failureEntries.add(new BarEntry(successEntries.size(), list[1]));
            xAxisValues.add(entry.getKey());

        }

    }

    /**
     * Override getGraphData method to account for the unique formatting in a binomial graph
     * and the fact that it uses two data sets
     * @return
     *     BarData representing the information to be displayed in the experiment's histogram
     */
    @Override
    protected BarData getGraphData() {

        setPlotValues();

        setUpGraphData();

        //create success data set and set the color to blue
        BarDataSet successDataSet = new BarDataSet(successEntries, "Success");
        successDataSet.setColor(Color.BLUE);

        //create failure data set and set the color to red
        BarDataSet failureDataSet = new BarDataSet(failureEntries, "Failure");
        failureDataSet.setColor(Color.RED);

        BarData theData = new BarData(successDataSet, failureDataSet);

        return theData;
    }

    /**
     * Fully set up the bar chart to be displayed
     * @param barChart
     *     Unformatted BarChart object
     */
    @Override
    public void createGraph(BarChart barChart) {

        if (((Binomial) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData();

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
