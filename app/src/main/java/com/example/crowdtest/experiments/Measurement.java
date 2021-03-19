package com.example.crowdtest.experiments;

import java.util.ArrayList;

/**
 * Measurement experiment class
 */
public class Measurement extends Experiment {

    // Measurement attributes
    private ArrayList <MeasurementTrial> trials;

    /**
     * Experiment constructor
     * @param owner
     *  Owner of the experiment
     * @param experimentID
     *  A unique ID for this experiment
     */
    public Measurement(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    /**
     * Adds a new trial to the experiment
     * @param trialInput
     *  The trial that is going to be submitted in the experiment
     */
    public void addTrial(double trialInput) {
        MeasurementTrial trial = new MeasurementTrial(trialInput);
        trials.add(trial);
    }

    /**
     * Function for setting the trials for the measurement experiment
     * @param trials
     *  ArrayList of measurement trials
     */
    public void setTrials(ArrayList<MeasurementTrial> trials) {
        this.trials = trials;
    }

    /**
     * Function for getting the trials for the measurement experiment
     * @return
     *  ArrayList of measurement trials
     */
    public ArrayList<MeasurementTrial> getTrials() {
        return trials;
    }
}
