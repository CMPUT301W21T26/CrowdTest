package com.example.crowdtest.experiments;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class Count extends Experiment {

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Count(Experimenter owner, String experimentID) {
        super(owner, experimentID);
        this.trials = new ArrayList<>();
        this.type = "count";
    }

    @Override
    public void addTrial(Trial trial) {
        trials.add((CountTrial) trial);
    }

}
