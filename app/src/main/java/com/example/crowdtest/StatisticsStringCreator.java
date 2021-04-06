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

public class StatisticsStringCreator {

    private Experiment experiment;

    private String additionalStatistics;

    public StatisticsStringCreator(Experiment experiment){

        this.experiment = experiment;

        this.additionalStatistics = "";

    }

    public String createStatisticsString(){

        ArrayList<Double> trialValues = new ArrayList<Double>();

        if (experiment instanceof Measurement) {


            for (MeasurementTrial trial: ((Measurement) experiment).getTrials()){

                trialValues.add(trial.getMeasurement());

            }


        } else if (experiment instanceof NonNegative) {

            for (NonNegativeTrial trial: ((NonNegative) experiment).getTrials()) {

                trialValues.add(new Double(trial.getCount()));

            }

        } else if (experiment instanceof Count) {

            for (CountTrial trial: ((Count) experiment).getTrials()) {

                trialValues.add(1.0);
            }

        } else if (experiment instanceof Binomial) {

            Binomial exp = (Binomial) experiment;

            for (BinomialTrial trial: exp.getTrials()){

                if (trial.isSuccess()) {

                    trialValues.add(1.0);

                } else {

                    trialValues.add(0.0);
                }
            }

            Integer successCount = exp.getSuccessCount();
            Integer failCount = exp.getFailCount();
            Integer totalTrials = successCount + failCount;
            Double successRate = ((trialValues.size() == 0) ? 0.0 : ((double) successCount / totalTrials)*100);

            additionalStatistics += "\nSuccesses: " + successCount
                    + "\nFailures: " + failCount
                    + "\nSuccess Rate: " + String.format("%.2f", successRate) + "%";

        }


        return additionalStatistics + (new StatisticsCalculator(trialValues)).getStatisticsString();


    }
}
