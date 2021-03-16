package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class Binomial extends Experiment {

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Binomial(String owner, String experimentID) {
        super(owner, experimentID);
        this.trials = new ArrayList<>();
        this.type = "binomial";
    }

    @Override
    public void addTrial(String trialID) {
        trials.add((trialID));
    }

}
