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

public class CountPlotManager extends PlotManager{

    protected TreeMap<LocalDate, Integer> plotValues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CountPlotManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<LocalDate, Integer>();

        label = "Measurement Entries in Range";

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<CountTrial> trials = ((Count) experiment).getTrials();

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setUpGraphData() {

        Integer totalCount = 0;

        LocalDate endDate = plotValues.lastKey().plusDays(1);

        LocalDate startDate = ((Count) experiment).getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Entry entry;

        int i =0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (plotValues.containsKey(date)){

                Integer dateCount = plotValues.get(date);
                totalCount += dateCount;
                entry = new Entry(i, totalCount);
            } else {

                entry = new Entry(i,totalCount);
            }

            entries.add(entry);
            String axisValue = date.format(dtf);
            xAxisValues.add(axisValue);
            i++;
        }

    }

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
