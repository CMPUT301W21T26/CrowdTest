package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * Class for creating line charts for Count experiments
 */
public class CountPlotManager extends PlotManager{

    protected TreeMap<LocalDate, Integer> plotValues;

    /**
     * Constructor for CountPlotManager
     * @param experiment
     *     Experiment with trial data to be displayed in a plot
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CountPlotManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<LocalDate, Integer>();

        label = "Measurement Entries in Range";

    }

    /**
     * Aggregates data to be displayed in plot using TreeMap plotValues object
     * Keys are dates of the experiment
     * Values are Integers with the number of count trials on that date
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<CountTrial> trials = ((Count) experiment).getValidTrials();

        for (int i=0; i < trials.size(); i++) {

            CountTrial trial = trials.get(i);

            LocalDate date = trial.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (plotValues.containsKey(date)){

                Integer oldValue = plotValues.get(date);

                plotValues.put(date, oldValue + 1);

            } else {

                plotValues.put(date, 1);

            }

        }

    }

    /**
     * Initializes array of that will contain ordered Entry objects
     * Initializes array that wil contain the X axis values
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setUpGraphData() {

        Integer totalCount = 0;

        LocalDate endDate = plotValues.lastKey().plusDays(1);

        LocalDate startDate = ((Count) experiment).getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Entry entry;

        int i =0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //iterate through dates from the date experiment was published until the last date a trial was added
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {

            //create Entry objects for each date, which each entry containing total number of Count trials until that date
            if (plotValues.containsKey(date)){

                Integer dateCount = plotValues.get(date);
                totalCount += dateCount;
                entry = new Entry(i, totalCount);
            } else {

                entry = new Entry(i,totalCount);
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
        if (((Count) experiment).getTrials().size() == 0) {

            return;
        }

        LineData theData = getGraphData();

        XAxis xAxis = lineChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        lineChart.setData(theData);

        return;
    }
}
