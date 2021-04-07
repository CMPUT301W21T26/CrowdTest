package com.example.crowdtest;


import java.util.ArrayList;
import java.util.Collections;

public class StatisticsCalculator {

    ArrayList<Double> trialValues;

    Double mean;

    Double median;

    Double stdDev;

    Double[] quartiles;

    public StatisticsCalculator(ArrayList<Double> trialValues) {

        this.trialValues = trialValues;

        Collections.sort(trialValues);

        initializeStatistics();

    }

    public void initializeStatistics(){

        mean = calculateMean();

        median = getMedian(trialValues);

        stdDev = getStdDev();

        quartiles = getQuartiles(median);

    };

    public String getStatisticsString(){

        String lowerQuartileString = ((quartiles[0] == null) ? "N/A" : String.format("%.2f", quartiles[0]));
        String upperQuartileString = ((quartiles[1] == null) ? "N/A" : String.format("%.2f", quartiles[1]));
        String stdDevString = ((stdDev == null) ? "N/A" : String.format("%.2f", stdDev));
        String meanString = ((mean == null) ? "N/A" : String.format("%.2f", mean));
        String medianString = ((median == null) ? "N/A" : String.format("%.2f", median));

        return  "\nTotal Trials: " + trialValues.size()
                + "\nMean: " + meanString
                + "\nStdDev: " + stdDevString
                + "\nMedian: " + medianString
                + "\nLower Quartile: " + lowerQuartileString
                + "\nUpper Quartile: " + upperQuartileString;

    }


    public Double calculateMean(){

        Integer n = trialValues.size();

        if (n == 0){

            return null;
        }

        double numerator = 0.0;

        for (int i=0; i < n; i++) {

            numerator += trialValues.get(i);

        }

        return numerator / (new Double(n));

    }

    private Double getMedian(ArrayList<Double> trialValues){

        Integer n = trialValues.size();

        if (n == 0){

            return null;
        }

        double median = 0.0;

        if(n % 2==1)
        {
            median = trialValues.get((n+1)/2-1);
        }
        else
        {
            median = (trialValues.get(n/2-1) + trialValues.get(n/2)) /2;
        }

        return median;
    }

    private Double getStdDev(){

        Integer n = trialValues.size();

        if (n==0){

            return null;
        }

        double baseValue = 0.0;

        for (int i = 0; i < trialValues.size(); i++)
        {
            baseValue += Math.pow(trialValues.get(i) - mean, 2) / n;
        }

        double stdDev = Math.sqrt(baseValue);

        return stdDev;
    }

    private Double[] getQuartiles(Double median){

        if (trialValues.size() < 4) {

            return new Double[]{null, null};
        }

        ArrayList<Double> list1 = new ArrayList<Double>();
        ArrayList<Double> list2 = new ArrayList<Double>();

        Double centerIndex = new Double(trialValues.size() - 1)/2;

        for (int i = 0; i < trialValues.size(); i++ ) {

            if (i < centerIndex) {

                list1.add(trialValues.get(i));

            } else if (i > centerIndex) {

                list2.add(trialValues.get(i));
            }

        }

        Double Q1 = getMedian(list1);
        Double Q3 = getMedian(list2);

        return new Double[]{Q1, Q3};

    }



}
