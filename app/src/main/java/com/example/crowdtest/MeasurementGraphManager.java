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

public class MeasurementGraphManager extends GraphManager{

    protected TreeMap<Integer, Integer> plotValues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MeasurementGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<Integer, Integer>();

        label = "Measurement Entries in Range";
    }

    protected void setPlotValues(Experiment experiment) {

        ArrayList<MeasurementTrial> trials = ((Measurement) experiment).getTrials();

        for (int i=0; i < trials.size(); i++) {

            MeasurementTrial trial = (MeasurementTrial) trials.get(i);

            Integer key;

            double remainder = trial.getMeasurement() % 10;

            if (remainder == 0) {

                key = (int) trial.getMeasurement();

            } else if (remainder > 0){

                key = (int) (trial.getMeasurement() - remainder) + 10;

            } else {

                key = (int) (trial.getMeasurement() - remainder);

            }

            if (plotValues.containsKey(key)){

                Integer oldValue = plotValues.get(key);

                plotValues.put(key, oldValue + 1);

            } else {

                plotValues.put(key, 1);

            }

        }

    }

    protected void setUpGraphData(){

        Integer largestUpperBound = plotValues.lastKey();

        Integer smallestUpperBound = plotValues.firstKey();

        int xValue = 0;

        for (int i = smallestUpperBound; i <= largestUpperBound; i+=10) {

            BarEntry entry;
            if (plotValues.containsKey(i)){
                entry = new BarEntry(xValue, plotValues.get(i));
            } else {

                entry = new BarEntry(xValue, 0);
            }
            barEntries.add(entry);

            String axisValue = String.valueOf(i-10) + " to " + String.valueOf(i);
            xAxisValues.add(axisValue);

            xValue++;
        }

    }

    @Override
    public void createGraph(BarChart barChart) {

        if (((Measurement) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData(experiment);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(5);

        return;

    }


}
