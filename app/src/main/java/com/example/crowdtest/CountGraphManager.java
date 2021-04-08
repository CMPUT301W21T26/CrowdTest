package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.MeasurementTrial;
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
 * Class for creating a histogram for a Count experiment
 */
public class CountGraphManager extends GraphManager{

    private TreeMap<String, Integer> plotValues;

    /**
     * Constructor for CountGraphManager
     * @param experiment
     *     Experiment with trial data to be displayed in a histogram
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CountGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<String, Integer>(Comparator.reverseOrder());

        label = "Total Count";
    }

    /**
     * Aggregates data to be displayed in histogram using TreeMap plotValues object
     * Keys are dates of the experiment
     * Values is the sum of count trials on that date
     */
    @Override
    protected void setPlotValues() {

        ArrayList<CountTrial> trials = ((Count) experiment).getTrials();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i=0; i < trials.size(); i++) {

            CountTrial trial = trials.get(i);

            String dateStr = sdf.format(trial.getTimestamp());

            if (plotValues.containsKey(dateStr)){

                Integer oldValue = plotValues.get(dateStr);

                plotValues.put(dateStr, oldValue + 1);

            } else {

                plotValues.put(dateStr, 1);

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
            xAxisValues.add(entry.getKey());

        }

    }

    /**
     * Fully set up the bar chart to be displayed
     * @param barChart
     *     Unformatted BarChart object
     */
    @Override
    public void createGraph(BarChart barChart) {

        if (((Count) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData();

        XAxis xAxis =  barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(5);

        return;
    }
}
