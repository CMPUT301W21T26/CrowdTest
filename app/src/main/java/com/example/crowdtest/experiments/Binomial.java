package com.example.crowdtest.experiments;


import java.util.ArrayList;

public class Binomial extends Experiment {
    private ArrayList<BinomialTrial> trials;
    private int successCount;
    private int failCount;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Binomial(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
        successCount = 0;
        failCount = 0;
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(boolean trialInput) {
        BinomialTrial trial = new BinomialTrial(trialInput);
        trials.add(trial);
        if (trialInput){
            successCount += 1;
        }
        else{
            failCount += 1;
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<BinomialTrial> getTrials() {
        return trials;
    }
}
