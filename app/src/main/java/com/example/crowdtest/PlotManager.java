package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

/**
 * Abstract parent class to all plot managers for different experiment types
 * Lays out the algorithm for setting up graph data
 */
public abstract class PlotManager {

    protected ArrayList<Entry> entries;

    protected ArrayList<String> xAxisValues;

    protected String label;

    protected Experiment experiment;

    /**
     * Plot manager constructor
     * @param experiment
     *     Experiment the plot is being made for
     */
    PlotManager(Experiment experiment){

        entries = new ArrayList<Entry>();

        xAxisValues = new ArrayList<>();

        this.experiment = experiment;

    }

    /**
     * Sets up line data to be displayed in a histogram and returns it
     * @return
     *    LineData representing the information to be displayed in the experiment's plot
     */
    protected LineData getGraphData(){

        setPlotValues();

        setUpGraphData();

        LineDataSet dataSet = new LineDataSet(entries, label);

        LineData theData = new LineData(dataSet);

        return theData;

    }

    /**
     * Returns IndexAxisValueFormatter with values to be displayed on histogram's X Axis
     * @return
     *    IndexAxisValueFormatter object with xAxis values
     */
    protected IndexAxisValueFormatter getXAxis() {

        return new IndexAxisValueFormatter(xAxisValues);
    }

    public abstract void createPlot(LineChart lineChart);

    protected abstract void setPlotValues();

    protected abstract void setUpGraphData();


}

