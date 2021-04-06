package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.MeasurementTrial;
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

public class CountGraphManager extends GraphManager{

    private TreeMap<String, Integer> plotValues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CountGraphManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<String, Integer>(Comparator.reverseOrder());

        label = "Total Count";
    }

    @Override
    protected void setPlotValues(Experiment experiment) {

        ArrayList<CountTrial> trials = ((Count) experiment).getTrials();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i=0; i < trials.size(); i++) {

            CountTrial trial = trials.get(i);

            String dateStr = sdf.format(trial.getTimestamp());

            if (plotValues.containsKey(dateStr)){

                Integer oldValue = plotValues.get(dateStr);

                plotValues.put(dateStr, oldValue + 1);

            } else {

                plotValues.put(dateStr, 1);

            }

        }

    }

    @Override
    protected void setUpGraphData() {

        for (Map.Entry<String, Integer> entry : plotValues.entrySet()) {

            barEntries.add(new BarEntry(barEntries.size(), entry.getValue()));
            xAxisValues.add(entry.getKey());

        }

    }

    @Override
    public void createGraph(BarChart barChart) {

        if (((Count) experiment).getTrials().size() == 0) {

            return;
        }

        BarData theData = getGraphData(experiment);

        XAxis xAxis =  barChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        barChart.setData(theData);

        barChart.setVisibleXRangeMaximum(5);

        return;
    }
}
