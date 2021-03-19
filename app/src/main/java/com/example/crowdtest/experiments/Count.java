package com.example.crowdtest.experiments;

import java.util.ArrayList;

/**
 * Count experiment class
 */
public class Count extends Experiment {

    // Count attributes
    private ArrayList <CountTrial> trials;

    /**
     * Count constructor
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Count(String owner, String experimentID) {
        super(owner, experimentID);
        this.trials = new ArrayList<>();
    }

    /**
     * Adds a new trial to the experiment
     */
    public void addTrial() {
        CountTrial trial = new CountTrial();
        trials.add(trial);
    }

    /**
     * Function for setting the trials of the count experiment
     * @param trials
     *  ArrayList of count trials
     */
    public void setTrials(ArrayList<CountTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials of the count experiment
     * @return
     *  ArrayList of count trials
     */
    public ArrayList<CountTrial> getTrials() {
        return trials;
    }
}
