package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.Experiment;
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
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Class for creating line charts for Binomial experiments
 */
public class BinomialPlotManager extends PlotManager{

    private TreeMap<LocalDate, Integer[]> plotValues;

    /**
     * Constructor for BinomialPlotManager
     * @param experiment
     *     Experiment with trial data to be displayed in a plot
     */
    public BinomialPlotManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<LocalDate, Integer[]>();

        label = "Success Rate (%)";

    }

    /**
     * Aggregates data to be displayed in plot using TreeMap plotValues object
     * Keys are dates of the experiment
     * Values are a array with the number of trials that were successes on that date
     * in the first index, and the total number of trials on that date in the second index.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<BinomialTrial> trials = ((Binomial) experiment).getTrials();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i=0; i < trials.size(); i++) {

            BinomialTrial trial = trials.get(i);

            Integer[] list;

            LocalDate date = trial.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (plotValues.containsKey(date)){

                list = plotValues.get(date);

            } else {

                list = new Integer[]{0,0};
            }

            if (trial.isSuccess()) {

                list[0] = list[0] + 1;

            } else {

                list[1] = list[1] + 1;
            }

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

        Integer totalSuccesses = 0;

        Integer totalTrials = 0;

        LocalDate endDate = plotValues.lastKey().plusDays(1);

        LocalDate startDate = ((Binomial) experiment).getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Entry entry;

        int i =0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //iterate through dates from the date experiment was published until the last date a trial was added
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {

            //create plot entries with each entry being the total success rate on that date
            if (plotValues.containsKey(date)){

                Integer[] dateValues = plotValues.get(date);
                totalSuccesses += dateValues[0];
                totalTrials += dateValues[0] + dateValues[1];
                Float successRate = ((float) totalSuccesses/totalTrials)*100;

                entry = new Entry(i, successRate);
            } else if (totalTrials == 0){

                entry = new Entry(i, 0);
            }
            else {
                entry = new Entry(i,((float) totalSuccesses/totalTrials)*100);
            }

            //add entry to the entry to the ArrayList and the xAxis string to the xAxisValues array list
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

        if (((Binomial) experiment).getTrials().size() == 0) {

            return;
        }

        LineData theData = getGraphData();

        XAxis xAxis = lineChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        lineChart.setData(theData);

        return;

    }
}
