package com.example.crowdtest.experiments;


import java.util.ArrayList;

public class Binomial extends Experiment {
    private ArrayList<BinomialTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Binomial(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(boolean trialInput) {
        BinomialTrial trial = new BinomialTrial(trialInput);
        trials.add(trial);
    }

    public int getSuccessCount() {
        int successCount = 0;
        for (int i =0; i < trials.size(); i++) {

            if (trials.get(i).isSuccess()) {
                successCount+=1;
            }
        }

        return successCount;
    }

    public int getFailCount() {
        int failCount = 0;
        for (int i =0; i < trials.size(); i++) {

            if (!trials.get(i).isSuccess()) {
                failCount+=1;
            }
        }

        return failCount;
    }

    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<BinomialTrial> getTrials() {
        return trials;
    }
}
