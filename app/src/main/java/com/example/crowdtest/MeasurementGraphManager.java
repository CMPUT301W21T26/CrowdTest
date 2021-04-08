package com.example.crowdtest;

import android.icu.util.Measure;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.Trial;
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
 * Creates histograms for Measurement experiments
 */
public class MeasurementGraphManager extends GraphManager{

    private TreeMap<Integer, Integer> plotValues;

    /**
     * Constructor for MeasurementGraphBuilder
     * @param experiment
     *     Experiment with trial data to be displayed in a histogram
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MeasurementGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<Integer, Integer>();

        //value for legend
        label = "Measurement Entries in Range";
    }

    /**
     * Aggregates data to be displayed in histogram using TreeMap plotValues object
     * Keys of plotValues will be set up as upper bound on "bins" of 10
     * Values are the count of trials with values within the "bin" ranges
     */
    protected void setPlotValues() {

        ArrayList<MeasurementTrial> trials = ((Measurement) experiment).getTrials();

        //iterate through experiment trials
        for (int i=0; i < trials.size(); i++) {

            MeasurementTrial trial = (MeasurementTrial) trials.get(i);

            Integer key;

            double remainder = trial.getMeasurement() % 10;


            if (remainder == 0) {
                //if the value is a multiple of 10,then the value is the key
                key = (int) trial.getMeasurement();

            } else if (remainder > 0){
                //if the remainder is positive, then the key is value rounded upwards to the nearest multiple of 10
                //this is the ceiling of the bin
                key = (int) (trial.getMeasurement() - remainder) + 10;

            } else {

                //if the remainder is negative, then the value is negative
                //remainder is removed and this becomes the key
                key = (int) (trial.getMeasurement() - remainder);

            }

            //incrememnt the count of trials for the key values
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
    protected void setUpGraphData(){

        Integer largestUpperBound = plotValues.lastKey();

        Integer smallestUpperBound = plotValues.firstKey();

        int xValue = 0;

        //start at the lowerst valued bin, iterate upwards in increments of 19 until the highest value bin is reached
        for (int i = smallestUpperBound; i <= largestUpperBound; i+=10) {

            BarEntry entry;

            if (plotValues.containsKey(i)){

                //if the key exists, create a bar entry with the key's value
                entry = new BarEntry(xValue, plotValues.get(i));

            } else {

                //if the key does not exist in plotValues, create a bar entry with a value of 0
                entry = new BarEntry(xValue, 0);

            }

            //Add bar entry
            barEntries.add(entry);

            //Create string for x axis and add it to the xAxisValues array
            String axisValue = String.valueOf(i-10) + " to " + String.valueOf(i);

            xAxisValues.add(axisValue);

            //increment the index representing the position of the bar entry on the x axis
            xValue++;
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
        if (((Measurement) experiment).getTrials().size() == 0) {

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
