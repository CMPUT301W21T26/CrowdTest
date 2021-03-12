package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class Binomial extends Experiment {
    private Collection <BinomialTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Binomial(Experimenter owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    @Override
    public void addTrial(Trial trial) {
        trials.add((BinomialTrial) trial);
    }
}
