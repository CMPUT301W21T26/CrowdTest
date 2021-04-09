package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class for creating line charts for NonNegative experiments
 */
public class NonNegativePlotManager extends PlotManager{

    protected TreeMap<LocalDate, Integer[]> plotValues;

    public NonNegativePlotManager(Experiment experiment) {

        super(experiment);

        plotValues = new TreeMap<LocalDate, Integer[]>();

        label = "Average NonNegative Count";

    }

    /**
     * Aggregates data to be displayed in plot using TreeMap plotValues object
     * Keys are dates of the experiment
     * Values are a array with the sum of NonNegative trial entry values on that date in
     * the first index, and the total number of trials on that date in the second index.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<NonNegativeTrial> trials = ((NonNegative) experiment).getValidTrials();


        for (int i=0; i < trials.size(); i++) {

             NonNegativeTrial trial = trials.get(i);

            Integer[] list;

            LocalDate date = trial.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (plotValues.containsKey(date)){

                list = plotValues.get(date);

            } else {

                list = new Integer[]{0,0};
            }

            list[0] += Math.toIntExact(trial.getCount());
            list[1] += 1;

            plotValues.put(date, list);

        }

    }

    /**
     * Initializes array of that will contain ordered Entry objects
     * Initializes array that wil contain the X axis values
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setUpGraphData() {

        Integer sumOfCounts = 0;

        Integer numOfCounts = 0;

        LocalDate endDate = plotValues.lastKey().plusDays(1);

        LocalDate startDate = ((NonNegative) experiment).getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Entry entry;

        int i =0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //iterate through dates from the date experiment was published until the last date a trial was added
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {

            //create Entry objects for each date, which each entry containing the overall average NonNegative Count trial value up until that date
            if (plotValues.containsKey(date)){

                Integer[] dateValues = plotValues.get(date);
                sumOfCounts += dateValues[0];
                numOfCounts += dateValues[1];
                Float avgMeasurement = (float) sumOfCounts/ numOfCounts;
                entry = new Entry(i, avgMeasurement);

            } else if (numOfCounts == 0){

                entry = new Entry(i, 0);
            }
            else {
                entry = new Entry(i,(float) sumOfCounts/numOfCounts);
            }

            //add entry and xAxis values to corresponding arrays
            entries.add(entry);
            String axisValue = date.format(dtf);
            xAxisValues.add(axisValue);
            i++;
        }

    }

    /**
     * Fully set up the line chart to be displayed
     * @param lineChart
     *     Unformatted LineChart object
     */
    @Override
    public void createPlot(LineChart lineChart) {

        if (((NonNegative) experiment).getTrials().size() == 0) {

            return;
        }

        LineData theData = getGraphData();

        XAxis xAxis = lineChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        lineChart.setData(theData);

        return;

    }
}
