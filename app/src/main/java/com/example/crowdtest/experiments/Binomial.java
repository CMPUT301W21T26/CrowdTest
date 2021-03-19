package com.example.crowdtest.experiments;

import java.util.ArrayList;

/**
 * Binomial experiment class
 */
public class Binomial extends Experiment {
    
    // Binomial attributes
    private ArrayList<BinomialTrial> trials;

    /**
     * Empty Binomial constructor
     */
    public Binomial() { }

    /**
     * Experiment constructor
     * @param owner
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public Binomial(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    /**
     * Adds a new trial to the experiment
     * @param trialInput
     *  The trial that is going to be submitted in the experiment
     */
    public void addTrial(boolean trialInput) {
        BinomialTrial trial = new BinomialTrial(trialInput);
        trials.add(trial);
    }

    /**
     * Function for getting the number of recorded success trials for the binomial experiment
     * @return
     *  Number of success trials
     */
    public int getSuccessCount() {
        int successCount = 0;
        for (int i =0; i < trials.size(); i++) {

            if (trials.get(i).isSuccess()) {
                successCount+=1;
            }
        }

        return successCount;
    }

    /**
     * Function for getting the number of recorded fail trials for the binomial experiment
     * @return
     *  Number of fail trials
     */
    public int getFailCount() {
        int failCount = 0;
        for (int i =0; i < trials.size(); i++) {

            if (!trials.get(i).isSuccess()) {
                failCount+=1;
            }
        }

        return failCount;
    }

    /**
     * Function for setting the trials for the binomial experiment
     * @param trials
     *  ArrayList of binomial trials
     */
    public void setTrials(ArrayList<BinomialTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials for the binomial experiment
     * @return
     *  ArrayList of binomial trials
     */
    public ArrayList<BinomialTrial> getTrials() {
        return trials;
    }
}
