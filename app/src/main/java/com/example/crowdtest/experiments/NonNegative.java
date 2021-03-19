package com.example.crowdtest.experiments;

import java.util.ArrayList;

/**
 * Class to represent a NonNegative experiment
 */
public class NonNegative extends Experiment {

    // NonNegative attributes
    private ArrayList <NonNegativeTrial> trials;

    /**
     * NonNegative experiment constructor
     * @param owner
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public NonNegative(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }
    /**
     * Adds a new trial to the experiment
     * @param trialInput
     *  The trial that is going to be submitted in the experiment
     */
    public void addTrial(int trialInput) {
        NonNegativeTrial trial = new NonNegativeTrial(trialInput);
        trials.add(trial);
    }

    /**
     * Function for setting the trials for the non-negative experiment
     * @param trials
     *  ArrayList of non-negative trials
     */
    public void setTrials(ArrayList<NonNegativeTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials for the non-negative experiment
     * @return
     *  ArrayList of non-negative trials
     */
    public ArrayList<NonNegativeTrial> getTrials() {
        return trials;
    }
}
