package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class NonNegative extends Experiment {
    private ArrayList <NonNegativeTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public NonNegative(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }
    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(int trialInput) throws Exception {
        NonNegativeTrial trial = new NonNegativeTrial(trialInput);
        trials.add(trial);
    }

    public void setTrials(ArrayList<NonNegativeTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<NonNegativeTrial> getTrials() {
        return trials;
    }
}
