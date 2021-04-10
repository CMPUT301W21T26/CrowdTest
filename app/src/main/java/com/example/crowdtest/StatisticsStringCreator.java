package com.example.crowdtest;

import com.example.crowdtest.experiments.Binomial;
import com.example.crowdtest.experiments.BinomialTrial;
import com.example.crowdtest.experiments.Count;
import com.example.crowdtest.experiments.CountTrial;
import com.example.crowdtest.experiments.Experiment;
import com.example.crowdtest.experiments.Measurement;
import com.example.crowdtest.experiments.MeasurementTrial;
import com.example.crowdtest.experiments.NonNegative;
import com.example.crowdtest.experiments.NonNegativeTrial;

import java.util.ArrayList;

/**
 * Get a different statistics string from StatisticsCalculator depending on experiment type
 * Get experiment specific statistics and add them to a generic string of statistics
 * Provide a string of statistics to be displayed in ExperimentStatisticsActivity
 */
public class StatisticsStringCreator {

    private Experiment experiment;

    private String additionalStatistics;

    private StatisticsCalculator statisticsCalculator;

    public StatisticsStringCreator(Experiment experiment){

        this.experiment = experiment;

        this.additionalStatistics = "";

    }

    /**
     * Create the statistics string
     * @return
     *    Returns a string with general and experiment specific statistics
     */
    public String createStatisticsString(){

        ArrayList<Double> trialValues = new ArrayList<Double>();

        if (experiment instanceof Measurement) {


            for (MeasurementTrial trial: ((Measurement) experiment).getValidTrials()){

                trialValues.add(trial.getMeasurement());

            }


        } else if (experiment instanceof NonNegative) {

            for (NonNegativeTrial trial: ((NonNegative) experiment).getValidTrials()) {

                trialValues.add(new Double(trial.getCount()));

            }

        } else if (experiment instanceof Count) {

            for (CountTrial trial: ((Count) experiment).getValidTrials()) {

                trialValues.add(1.0);
            }

        } else if (experiment instanceof Binomial) {

            Binomial exp = (Binomial) experiment;

            for (BinomialTrial trial: exp.getValidTrials()){

                if (trial.isSuccess()) {

                    trialValues.add(1.0);

                } else {

                    trialValues.add(0.0);
                }
            }

            Integer successCount = exp.getValidSuccessCount();
            Integer failCount = exp.getValidFailCount();
            Integer totalTrials = successCount + failCount;
            Double successRate = ((trialValues.size() == 0) ? 0.0 : ((double) successCount / totalTrials)*100);

            additionalStatistics += "\nSuccesses: " + successCount
                    + "\nFailures: " + failCount
                    + "\nSuccess Rate: " + String.format("%.2f", successRate) + "%";

        }


        statisticsCalculator = new StatisticsCalculator(trialValues);
        return additionalStatistics + statisticsCalculator.getStatisticsString();


    }
}
