package com.example.crowdtest;

import com.example.crowdtest.experiments.Experiment;

import java.util.ArrayList;

/**
 *
 */
public class Owner extends Experimenter {

    // Owner attributes
    ArrayList<String> ownedExperiments;
    ExperimentManager experimentManager;

    /**
     * Owner constructor
     * @param userProfile
     * @param experimentManager
     */
    public Owner(UserProfile userProfile, ExperimentManager experimentManager) {
        super(userProfile);

        // Initialize Owner attributes
        setStatus("owner");
        ownedExperiments = new ArrayList<>();
        this.experimentManager = experimentManager;
    }

    /**
     * Function for publishing an experiment to the database
     * @param experiment
     */
    public void publishExperiment(Experiment experiment) {
        ownedExperiments.add(experiment.getExperimentID());
    }

    /**
     * Function for removing experiment from owner's list
     * @param experiment
     */
    public void unpublishExperiment(Experiment experiment) {
        ownedExperiments.remove(experiment.getExperimentID());
    }

    /**
     *
     */
    public void archiveExperiment() {

    }
}
