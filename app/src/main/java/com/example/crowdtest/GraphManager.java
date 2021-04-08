package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.Trial;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract parent class to all graph managers for different experiment types
 * Lays out the algorithm for setting up graph data
 */
public abstract class GraphManager {

    protected ArrayList<BarEntry> barEntries;

    protected ArrayList<String> xAxisValues;

    protected String label;

    protected Experiment experiment;

    /**
     * Graph manager constructor
     * @param experiment
     *     Experiment the graph is being made for
     */
    public GraphManager(Experiment experiment){

        this.barEntries = new ArrayList<BarEntry>();

        this.xAxisValues = new ArrayList<>();

        this.experiment = experiment;

    }

    /**
     * Sets up bar data to be displayed in a histogram and returns it
     * @return
     *    BarData representing the information to be displayed in the experiment's histogram
     */
    protected BarData getGraphData(){

        setPlotValues();

        setUpGraphData();

        BarDataSet barDataSet = new BarDataSet(barEntries, label);

        BarData theData = new BarData(barDataSet);

        return theData;

    }

    /**
     * Returns IndexAxisValueFormatter with values to be displayed on histogram's X Axis
     * @return
     *    IndexAxisValueFormatter object with xAxis values
     */
    protected IndexAxisValueFormatter getXAxis(){

        return new IndexAxisValueFormatter(xAxisValues);
    }

    public abstract void createGraph(BarChart barChart);

    protected abstract void setPlotValues();

    protected abstract void setUpGraphData();

}
