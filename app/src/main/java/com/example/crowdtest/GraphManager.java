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

public abstract class GraphManager {

    protected ArrayList<BarEntry> barEntries;

    protected ArrayList<String> xAxisValues;

    protected String label;

    protected Experiment experiment;

    GraphManager(Experiment experiment){

        this.barEntries = new ArrayList<BarEntry>();

        this.xAxisValues = new ArrayList<>();

        this.experiment = experiment;

    }

    protected BarData getGraphData(Experiment experiment){

        setPlotValues(experiment);

        setUpGraphData();

        BarDataSet barDataSet = new BarDataSet(barEntries, label);

        BarData theData = new BarData(barDataSet);

        return theData;

    }

    protected IndexAxisValueFormatter getXAxis(){

        return new IndexAxisValueFormatter(xAxisValues);
    }

    public abstract void createGraph(BarChart barChart);

    protected abstract void setPlotValues(Experiment experiment);

    protected abstract void setUpGraphData();

}
