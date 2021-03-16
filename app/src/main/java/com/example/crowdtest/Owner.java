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
     * @param type
     */
    public void publishExperiment(String type) {
        Experiment experiment = experimentManager.publishExperiment(this.getUserProfile().getUsername(), type);
        ownedExperiments.add(experiment.getExperimentID());

    }

    /**
     * Function for un-publishing an experiment from the database
     * @param experiment
     */
    public void unpublishExperiment(Experiment experiment) {
        ownedExperiments.remove(experiment.getExperimentID());
        experimentManager.unpublishExperiment(experiment);
    }

    /**
     *
     */
    public void archiveExperiment() {

    }
}
