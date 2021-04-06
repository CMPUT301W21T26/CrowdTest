package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.Trial;
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

    GraphManager(){

        barEntries = new ArrayList<BarEntry>();

        xAxisValues = new ArrayList<>();

    }

    public BarData getGraphData(Experiment experiment){

        setPlotValues(experiment);

        setUpGraphData();

        BarDataSet barDataSet = new BarDataSet(barEntries, label);

        BarData theData = new BarData(barDataSet);

        return theData;

    }

    public abstract IndexAxisValueFormatter getXAxis();

    protected abstract void setPlotValues(Experiment experiment);

    protected abstract void setUpGraphData();
}
