package com.example.crowdtest;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Calculate statistics for an experiment, and provide a string containing statistical values
 */
public class StatisticsCalculator {

    ArrayList<Double> trialValues;

    Double mean;

    Double median;

    Double stdDev;

    Double[] quartiles;

    /**
     * Constructo
     * @param trialValues
     *     An ArrayList<Double> of the values of each of an experiment's trials
     */
    public StatisticsCalculator(ArrayList<Double> trialValues) {

        this.trialValues = trialValues;

        Collections.sort(trialValues);

        initializeStatistics();

    }

    /**
     * Calculate all of th different statistics, which are stored as attributes
     */
    public void initializeStatistics(){

        mean = calculateMean();

        median = getMedian(trialValues);

        stdDev = getStdDev();

        quartiles = getQuartiles();

    };

    /**
     * Return a string with all of an experiment's statistics
     * @return
     */
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


    /**
     * Calculate mean of trial values
     * @return
     */
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

    /**
     * Calculate median of trialValues
     * Helper function for calculating quartiles
     * @param trialValues
     *     An array of the trialValues for which the median will be calculated
     * @return
     */
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

    /**
     * Calculate standard deviation for trial values
     * @return
     */
    public Double getStdDev(){

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

    /**
     * Calculate quartiles for trial values, and return them in array
     * @return
     *     An array containing lower and upper quartile values
     */
    private Double[] getQuartiles(){

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
