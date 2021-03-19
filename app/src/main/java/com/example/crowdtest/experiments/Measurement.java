package com.example.crowdtest.experiments;

import android.icu.util.MeasureUnit;

import com.example.crowdtest.Experimenter;

import java.util.ArrayList;
import java.util.Collection;

public class Measurement extends Experiment {
    private ArrayList <MeasurementTrial> trials;

    /**
     * Experiment constructor
     *
     * @param owner         Owner of the experiment
     * @param experimentID  A unique ID for this experiment
     */
    public Measurement(String owner, String experimentID) {
        super(owner, experimentID);
        trials = new ArrayList<>();
    }

    /**
     * Adds a new trial to the experiment
     *
     * @param trialInput The trial that is going to be submitted in the experiment
     */
    public void addTrial(double trialInput) {
        MeasurementTrial trial = new MeasurementTrial(trialInput);
        trials.add(trial);
    }

    public void setTrials(ArrayList<MeasurementTrial> trials) {
        this.trials = trials;
    }

    public ArrayList<MeasurementTrial> getTrials() {
        return trials;
    }
}
