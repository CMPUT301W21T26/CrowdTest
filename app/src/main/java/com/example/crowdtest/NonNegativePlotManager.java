package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

public class NonNegativePlotManager extends PlotManager{

    protected TreeMap<LocalDate, Integer[]> plotValues;

    public NonNegativePlotManager(Experiment experiment) {

        super(experiment);

        plotValues = new TreeMap<LocalDate, Integer[]>();

        label = "Average NonNegative Count";

    }

    @Override
    public IndexAxisValueFormatter getXAxis() {
        return new IndexAxisValueFormatter(xAxisValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<NonNegativeTrial> trials = ((NonNegative) experiment).getTrials();


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

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
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

            entries.add(entry);
            String axisValue = date.format(dtf);
            xAxisValues.add(axisValue);
            i++;
        }

    }
}
