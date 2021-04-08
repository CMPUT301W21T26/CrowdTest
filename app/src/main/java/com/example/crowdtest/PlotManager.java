package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public abstract class PlotManager {

    protected ArrayList<Entry> entries;

    protected ArrayList<String> xAxisValues;

    protected String label;

    protected Experiment experiment;

    PlotManager(Experiment experiment){

        entries = new ArrayList<Entry>();

        xAxisValues = new ArrayList<>();

        this.experiment = experiment;

    }

    protected LineData getGraphData(){

        setPlotValues();

        setUpGraphData();

        LineDataSet dataSet = new LineDataSet(entries, label);

        LineData theData = new LineData(dataSet);

        return theData;

    }

    protected IndexAxisValueFormatter getXAxis() {

        return new IndexAxisValueFormatter(xAxisValues);
    }

    public abstract void createPlot(LineChart lineChart);

    protected abstract void setPlotValues();

    protected abstract void setUpGraphData();


}

