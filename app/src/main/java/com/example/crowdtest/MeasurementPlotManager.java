package com.example.crowdtest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

public class MeasurementPlotManager extends PlotManager {

    protected TreeMap<LocalDate, Double[]> plotValues;

    public MeasurementPlotManager(Experiment experiment){

        super(experiment);

        plotValues = new TreeMap<LocalDate, Double[]>();

        label = "Average Measurement";

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setPlotValues() {

        ArrayList<MeasurementTrial> trials = ((Measurement) experiment).getTrials();

        for (int i=0; i < trials.size(); i++) {

            MeasurementTrial trial = trials.get(i);

            Double[] list;

            LocalDate date = trial.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (plotValues.containsKey(date)){

                list = plotValues.get(date);

            } else {

                list = new Double[]{0.0,0.0};
            }

            list[0] += trial.getMeasurement();
            list[1] += 1;

            plotValues.put(date, list);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void setUpGraphData() {

        Double sumOfMeasurements = 0.0;

        Double numOfMeasurements = 0.0;

        LocalDate endDate = plotValues.lastKey().plusDays(1);

        LocalDate startDate = ((Measurement) experiment).getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Entry entry;

        int i =0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (plotValues.containsKey(date)){

                Double[] dateValues = plotValues.get(date);
                sumOfMeasurements += dateValues[0];
                numOfMeasurements += dateValues[1];
                Float avgMeasurement = sumOfMeasurements.floatValue()/ numOfMeasurements.floatValue();
                entry = new Entry(i, avgMeasurement);

            } else if (numOfMeasurements == 0){

                entry = new Entry(i, 0);
            }
            else {
                entry = new Entry(i,sumOfMeasurements.floatValue()/numOfMeasurements.floatValue());
            }

            entries.add(entry);
            String axisValue = date.format(dtf);
            xAxisValues.add(axisValue);
            i++;
        }

    }

    @Override
    public void createPlot(LineChart lineChart) {

        if (((Measurement) experiment).getTrials().size() == 0) {

            return;
        }

        LineData theData = getGraphData();

        XAxis xAxis = lineChart.getXAxis();

        xAxis.setValueFormatter(getXAxis());

        lineChart.setData(theData);

        return;

    }
}
