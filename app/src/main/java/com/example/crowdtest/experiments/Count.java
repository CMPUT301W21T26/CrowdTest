package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class Count extends Experiment {
    private ArrayList <CountTrial> trials;

    /**
     * Experiment constructor
     *
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

    public void setTrials(ArrayList<CountTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<CountTrial> getTrials() {
        return trials;
    }
}
